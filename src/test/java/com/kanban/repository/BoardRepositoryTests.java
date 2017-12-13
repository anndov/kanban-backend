package com.kanban.repository;

import com.kanban.model.Board;
import com.kanban.security.model.User;
import com.kanban.security.repository.UserRepository;
import com.kanban.security.services.UserService;
import com.kanban.service.BoardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by david on 11-Dec-17.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class BoardRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void delete_user_from_membership() {
        User user = new User();
        user.setUsername("username");
        user.setEnabled(true);
        user.setValidated(true);
        entityManager.persist(user);
        entityManager.flush();
        Board board = new Board();
        board.setParticipants(Collections.singleton(user));
        entityManager.persist(board);
        entityManager.flush();

        User found = userRepository.findUsersByUsernameLikeAndBoardIdAndEnabledAndValidated(user.getUsername(), board.getId()).get(0);

        assertThat(found.getId()).isEqualTo(user.getId());

        /*board.getParticipants().remove(user);
        user.getBoards().remove(board);*/
        /*boardRepository.save(board);

        User deleted = userRepository.findUsersByUsernameLikeAndBoardIdAndEnabledAndValidated(user.getUsername(), board.getId()).get(0);
        assertThat(deleted).isEqualTo(null);*/

    }
}
