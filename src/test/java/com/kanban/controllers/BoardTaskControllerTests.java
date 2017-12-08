package com.kanban.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kanban.model.Board;
import com.kanban.model.BoardTask;
import com.kanban.security.model.User;
import com.kanban.service.BoardTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BoardTaskController.class, secure = false)
public class BoardTaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @MockBean
    private BoardTaskService boardTaskService;

    public BoardTask getTask() {
        BoardTask task = new BoardTask();
        task.setName("Name");
        task.setDescription("Name");
        task.setBoardColumnId(11L);
        task.setBoardId(111L);
        task.setColor("green");
        task.setDueDate(new Date());
        task.setAssignee("kermit");
        task.setOwner("kermit");

        return task;
    }

    public Page getTasks() {
        return new PageImpl<BoardTask>(Arrays.asList(getTask(), getTask()));
    }

    @Test
    public void save_test() throws Exception {
        BoardTask task = getTask();

        BoardTask added = getTask();

        Mockito.when(this.boardTaskService.save(any(BoardTask.class))).thenReturn(added);

        this.mockMvc.perform(post("/rest/tasks")
                .content(this.gson.toJson(task))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void update_test() throws Exception {
        BoardTask task = getTask();
        task.setId(111L);

        BoardTask updated = getTask();
        updated.setId(111L);
        updated.setName("Updated");

        Mockito.when(this.boardTaskService.save(any(BoardTask.class))).thenReturn(updated);

        this.mockMvc.perform(put("/rest/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(task)))
                .andExpect(status().isCreated());
    }

    @Test
    public void delete_task_test() throws Exception {
        this.mockMvc.perform(delete("/rest/tasks/" + 111L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void find_task_by_board_id_test() throws Exception {
        Mockito.when(this.boardTaskService.findByBoardIdAndDeletedFalse(anyLong())).thenReturn(getTasks());

        this.mockMvc.perform(get("/rest/tasks/board-id/" + 111L)).andExpect(status().isOk());
    }

    @Test
    public void find_task_by_board_column_id_test() throws Exception {
        Mockito.when(this.boardTaskService.findByBoardIdAndDeletedFalse(anyLong())).thenReturn(getTasks());

        this.mockMvc.perform(get("/rest/tasks/board-column-id/" + 111L)).andExpect(status().isOk());
    }
}
