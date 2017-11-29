package com.kanban.controllers;

import com.google.gson.Gson;
import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import com.kanban.security.model.User;
import com.kanban.security.services.UserService;
import com.kanban.service.BoardService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BoardControllerTests extends AbstractControllerTest {

    private User user;

    private Board b;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Before
    public void setup() {
        boardService.deleteAllInBatch();
        if(userService.findByUsername("board") != null)
        userService.delete(userService.findByUsername("board").getId());
        user = userService.save(new User("board", "12345", "board.board@gmail.com", true));

        b = new Board();
        b.setName("First board");
//        b.setOwner(this.user);
        b.setParticipants(Collections.singleton(this.user));
        b.setBoardColumns(new HashSet<>(Collections.singleton(new BoardColumn("To do", 1))));
        b = boardService.save(b);
    }

    @Test
    public void updateTest() throws Exception {
        this.b.setName("My Board Updated");
        String boardJson = new Gson().toJson(this.b);
        mockMvc.perform(put("/rest/boards")
                .contentType(contentType)
                .content(boardJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is("My Board Updated")));
    }

    @Test
    public void saveTest() throws Exception {
        Board board = new Board();
        board.setName("My board");
        board.setOwner(this.user);
        Set<User> users = new HashSet<>();
        users.add(this.user);
        board.setParticipants(users);
        board.setBoardColumns(new HashSet<>(Collections.singleton(new BoardColumn("To do", 1))));

        String boardJson = new Gson().toJson(board);

        mockMvc.perform(post("/rest/boards")
                .contentType(contentType)
                .content(boardJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void findByIdTest() throws Exception {
        mockMvc.perform(get("/rest/boards/" + b.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(b.getName())));

        mockMvc.perform(get("/rest/boards/" + "13213123132"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByParticipants_UsernameTest() throws Exception {
        mockMvc.perform(get("/rest/boards/username/" + user.getUsername()))
                .andExpect(status().isOk());
    }

    @Test
    public void findUserByBoardId() throws Exception {
        mockMvc.perform(get("/rest/users/board-id/" + b.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void findUserByBoardIdAndUsername() throws Exception {
        mockMvc.perform(get("/rest/users/username/" + user.getUsername() + "/board-id/" + b.getId()))
                .andExpect(status().isOk());
    }
}
