package com.kanban.bpmn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import com.kanban.model.BoardTask;
import com.kanban.security.model.User;
import com.kanban.security.services.UserService;
import com.kanban.service.BoardService;
import com.kanban.service.bpmn.BoardTaskBpmnService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class BoardTaskBpmnControllerTests {

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype());

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardTaskBpmnService boardTaskBpmnService;

    @Autowired
    private UserService userService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    private ObjectMapper mapper;

    private User user;

    private Board board;

    private BoardColumn backLog;
    private BoardColumn inProgress;

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

        mapper = new ObjectMapper();

        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    private BoardTask create() {
        BoardTask  bTask = new BoardTask();
        bTask.setName("Sample");
        bTask.setDescription("Sample Desc");
        bTask.setBoardColumnId(backLog.getId());
        bTask.setBoardId(board.getId());
        bTask.setColor("yellow");
        bTask.setDueDate(new Date());
        bTask.setAssignee("kermit");
        bTask.setOwner("kermit");
        return bTask;
    }

    @Test
    public void addTask() throws Exception {
        String jsonBoard = mapper.writeValueAsString(create());

        mockMvc.perform(post("/rest/boardtasks")
                .contentType(contentType)
                .content(jsonBoard))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Sample")));
    }

    @Test
    public void updateTask() throws Exception {
        BoardTask bTask = boardTaskBpmnService.addTask(create());
        bTask.setName("Updated");
        bTask.setDescription("Updated");
        bTask.setBoardColumnId(inProgress.getId());
        bTask.setBoardId(board.getId());
        bTask.setColor("green");
        bTask.setDueDate(new Date());
        bTask.setAssignee("kermit");
        bTask.setOwner("kermit");

        mockMvc.perform(put("/rest/boardtasks")
                .content(mapper.writeValueAsString(bTask))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated")));
    }

    @Test
    public void findTasksByBoardColumnId() throws Exception {
        BoardTask boardTask = boardTaskBpmnService.addTask(create());
        mockMvc.perform(get("/rest/boardtasks/boardColumnId/" + backLog.getId()))
                .andExpect(status().isOk());
        boardTaskBpmnService.deleteTask(boardTask.getProcessInstanceId());
    }

    @Test
    public void findTasksByBoardId() throws Exception {
        BoardTask boardTask = boardTaskBpmnService.addTask(create());
        mockMvc.perform(get("/rest/boardtasks/boardId/" + backLog.getId()))
                .andExpect(status().isOk());
        boardTaskBpmnService.deleteTask(boardTask.getProcessInstanceId());
    }

    @Test
    public void deleteTask() throws Exception {
        BoardTask boardTask = boardTaskBpmnService.addTask(create());
        mockMvc.perform(delete("/rest/process-instance/" + boardTask.getProcessInstanceId()))
                .andExpect(status().isOk());
    }

    @Test
    public void completeTask() throws Exception {
        BoardTask boardTask = boardTaskBpmnService.addTask(create());

        mockMvc.perform(post("/rest/boardtasks/" + boardTask.getTaskId() + "/complete"))
                .andExpect(status().isNoContent());
    }

}
