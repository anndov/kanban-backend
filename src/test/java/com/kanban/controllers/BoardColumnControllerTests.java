package com.kanban.controllers;

import com.google.gson.Gson;
import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import com.kanban.service.BoardColumnService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by david on 21-Dec-17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = BoardColumnController.class, secure = false)
public class BoardColumnControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardColumnService boardColumnService;

    @Test
    public void update_test() throws Exception {
        BoardColumn boardColumn = new BoardColumn();
        boardColumn.setName("name");
        boardColumn.setCurrent(0);

        BoardColumn boardColumnUpdated = new BoardColumn();
        boardColumnUpdated.setName("name-updated");
        boardColumnUpdated.setCurrent(1);

        Mockito.when(boardColumnService.save(any(BoardColumn.class))).thenReturn(boardColumnUpdated);

        this.mockMvc.perform(put("/rest/board-column/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(boardColumn)))
                .andExpect(status().isCreated());

    }

}
