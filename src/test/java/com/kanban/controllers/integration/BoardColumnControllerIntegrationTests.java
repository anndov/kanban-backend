package com.kanban.controllers.integration;

import com.google.gson.Gson;
import com.kanban.model.BoardColumn;
import com.kanban.service.BoardColumnService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by david on 22-Dec-17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureTestDatabase
public class BoardColumnControllerIntegrationTests {

    @Autowired
    private BoardColumnService boardColumnService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void update_test() throws Exception {
        BoardColumn boardColumn = new BoardColumn();
        boardColumn.setName("name");
        boardColumn.setCurrent(1);
        boardColumn = boardColumnService.save(boardColumn);

        BoardColumn boardColumnUpdated = new BoardColumn();
        boardColumnUpdated.setName("nameUpdated");
        boardColumnUpdated.setCurrent(2);
        boardColumnUpdated.setId(boardColumn.getId());

        this.mockMvc.perform(put("/rest/board-column/")
                .content(new Gson().toJson(boardColumnUpdated))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        assertThat(boardColumnService.findOne(boardColumn.getId()).getCurrent()).isEqualTo(boardColumnUpdated.getCurrent());
    }
}
