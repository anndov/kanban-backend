package com.kanban.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import com.kanban.model.BoardTask;
import com.kanban.security.model.User;
import com.kanban.security.services.UserService;
import com.kanban.service.BoardService;
import com.kanban.service.BoardTaskService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardTaskControllerTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype());

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardTaskService boardTaskService;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    private Board board;

    private User user;

    private BoardColumn backLog;
    private BoardColumn inProgress;

    private ObjectMapper objectMapper;

    private BoardTask task;

    @Before
    public void setUp() {
        boardService.deleteAllInBatch();
        if(userService.findByUsername("kermit") != null)
            userService.delete(userService.findByUsername("kermit").getId());
        user = userService.save(new User("kermit", "12345", "kermit.board@gmail.com", true));

        board = new Board();
        board.setName("First board");
        board.setOwner(user);
        board.setParticipants(Collections.singleton(user));
        board.setBoardColumns(new HashSet<>(Arrays.asList(new BoardColumn("Backlog", 1), new BoardColumn("In progress", 2))));
        board = boardService.save(board);

        board.getBoardColumns().forEach(boardColumn -> {
            if (boardColumn.getName().equals("Backlog"))
                backLog = boardColumn;

            if (boardColumn.getName().equals("In progress"))
                inProgress = boardColumn;
        });

        mockMvc = webAppContextSetup(webApplicationContext).build();

        objectMapper = new ObjectMapper();

        task = boardTaskService.save(newTask());
    }

    @After
    public void tearDown() {
        boardTaskService.deletePermanently(task.getId());
    }

    public BoardTask newTask() {
        BoardTask task = new BoardTask();
        task.setName("Updated");
        task.setDescription("Updated");
        task.setBoardColumnId(inProgress.getId());
        task.setBoardId(board.getId());
        task.setColor("green");
        task.setDueDate(new Date());
        task.setAssignee("kermit");
        task.setOwner("kermit");

        return task;
    }

    @Test
    public void addTask() throws Exception {
        mockMvc.perform(post("/rest/tasks")
                .contentType(contentType)
                .content(objectMapper.writeValueAsString(newTask())))
                .andExpect(status().isCreated());
    }

    @Test
    public void findTasksByBoardColumnId() throws Exception {
        mockMvc.perform(get("/rest/tasks/board_column_id/" + task.getBoardColumnId()))
                .andExpect(status().isOk());

    }

    @Test
    public void findTasksByBoardId() throws Exception {
        mockMvc.perform(get("/rest/tasks/board_id/" + task.getBoardId()))
                .andExpect(status().isOk());

    }

    @Test
    public void updateTask() throws Exception {
        mockMvc.perform(put("/rest/tasks")
                .content(objectMapper.writeValueAsString(task))
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTask() throws Exception {
        mockMvc.perform(delete("/rest/tasks/id/" + task.getId()))
                .andExpect(status().isOk());
    }
}
