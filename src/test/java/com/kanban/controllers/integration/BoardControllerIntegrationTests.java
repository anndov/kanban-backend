package com.kanban.controllers.integration;

import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import com.kanban.security.model.User;
import com.kanban.security.services.UserService;
import com.kanban.service.BoardColumnService;
import com.kanban.service.BoardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by david on 22-Dec-17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureTestDatabase
public class BoardControllerIntegrationTests {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardColumnService boardColumnService;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void board_delete_test() throws Exception {
        User user = new User();
        user.setEnabled(true);
        user.setEmail("11email.emial@ema.com");
        user = userService.save(user);

        BoardColumn boardColumn = new BoardColumn();
        boardColumn.setName("name");
        boardColumn = boardColumnService.save(boardColumn);

        Board board = new Board();
        board.setName("name");
        board.setOwner(user);
        board.setBoardColumns(Collections.singleton(boardColumn));
        board.setParticipants(Collections.singleton(user));
        board = boardService.save(board);

        this.mockMvc.perform(delete("/rest/boards/" + board.getId()))
                .andExpect(status().isNoContent());
    }
}
