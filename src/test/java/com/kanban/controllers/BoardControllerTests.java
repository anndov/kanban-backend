package com.kanban.controllers;

import com.google.gson.Gson;
import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import com.kanban.security.model.User;
import com.kanban.service.BoardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BoardController.class, secure=false)
public class BoardControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    public User getUser() {
        return new User("user", "12345", "user.user@gmail.com", true);
    }

    public Board getBoard() {
        Board board = new Board();
        board.setName("My board");
        board.setOwner(getUser());
        Set<User> users = new HashSet<>();
        users.add(getUser());
        board.setParticipants(users);
        board.setBoardColumns(new HashSet<>(Collections.singleton(new BoardColumn("To do", 1))));

        return board;
    }

    @Test
    public void create_test() throws Exception {
        Board board = getBoard();

        Board added = getBoard();
        added.setId(111111L);

        Mockito.when(
                this.boardService.save(any(Board.class))
        ).thenReturn(added);

        this.mockMvc.perform(post("/rest/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(board)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void update_test() throws Exception {
        Board board = new Board();
        board.setId(111111L);

        Board updated = getBoard();
        updated.setName("Updated");
        updated.setId(111111L);

        Mockito.when(
                this.boardService.save(any(Board.class)))
                .thenReturn(updated);

        this.mockMvc.perform(put("/rest/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(board)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Updated")));
    }

    @Test
    public void find_by_id() throws Exception {
        Board board = getBoard();
        board.setId(1111L);
        Mockito.when(this.boardService.findOne(board.getId())).thenReturn(board);

        this.mockMvc.perform(get("/rest/boards/" + board.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(board.getId().intValue())));
    }
}
