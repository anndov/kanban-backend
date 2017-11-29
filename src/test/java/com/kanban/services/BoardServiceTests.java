package com.kanban.services;

import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import com.kanban.security.model.User;
import com.kanban.security.services.UserService;
import com.kanban.service.BoardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    private User u;

    private Board b;

    @Before
    public void setup() {
        boardService.deleteAllInBatch();
        if(userService.findByUsername("board") != null)
            userService.delete(userService.findByUsername("board").getId());
        u = userService.save(new User("board", "12345", "board.board@gmail.com", true));

        b = new Board();
        b.setName("First board");
        b.setOwner(u);
        b.setParticipants(Collections.singleton(u));
        b.setBoardColumns(new HashSet<>(Collections.singleton(new BoardColumn("To do", 1))));
        b = boardService.save(b);
    }

    @Test
    public void findAllByParticipantId() {
        assertThat(1).isEqualTo(boardService.findByParticipants_Username(u.getUsername()).size());
    }

    @Test
    public void findAllByParticipantUserNameLike() {
        assertThat(1).isEqualTo(boardService.findByParticipants_UsernameLike(u.getUsername()).size());
    }

    @Test
    public void findUsersByBoardId() {
        assertThat(1).isEqualTo(boardService.findUsersByBoardId(b.getId()).size());
    }
}
