package com.kanban.controllers;

import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import com.kanban.security.model.User;
import com.kanban.security.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by david on 29-Oct-17.
 */
public class BoardControllerTests extends AbstractControllerTest {

    private User user;

    @Autowired
    private UserService userService;

    @Before
    public void setup() {
        if(userService.findByUsername("board") != null)
        userService.delete(userService.findByUsername("board").getId());
        user = userService.save(new User("board", "12345", "board.board@gmail.com", true));
    }

    @Test
    public void saveTest() throws Exception {
        Board board = new Board();
        board.setName("My board");
        board.setOwner(this.user);
        board.setParticipants(new HashSet<>(Collections.singleton(this.user)));
        board.setBoardColumns(new HashSet<>(Collections.singleton(new BoardColumn("To do", 1))));
        String boardJson = json(board);
        System.out.println(boardJson);
        mockMvc.perform(post("/boards")
                .contentType(contentType)
                .content(boardJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType));
    }
}
