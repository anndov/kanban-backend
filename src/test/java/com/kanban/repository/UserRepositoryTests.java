package com.kanban.repository;

import com.kanban.model.Board;
import com.kanban.security.model.User;
import com.kanban.security.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void find_by_username_like_and_board_id_and_enabled_true_and_validated_true_test() {

        User user = new User();
        user.setUsername("test");
        user.setPassword(new BCryptPasswordEncoder().encode("test"));
        user.setEnabled(true);
        user.setValidated(true);
        user.setEmail("email.email@email.com");
        this.entityManager.persist(user);
        this.entityManager.flush();

        Board board = new Board();
        board.setName("Name");
        board.setOwner(user);
        board.setParticipants(Collections.singleton(user));

        this.entityManager.persist(board);
        this.entityManager.flush();

        List<User> users = this.userRepository.findUsersByUsernameLikeAndBoardIdAndEnabledAndValidated("test", board.getId());

        assertThat(users.get(0).getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void find_by_email_test() {
        User user = new User();
        user.setEmail("david.david@gmail.com");
        user.setEnabled(false);
        this.entityManager.persist(user);
        this.entityManager.flush();

        User foun = this.userRepository.findByEmail(user.getEmail());

        assertThat(foun.getEmail()).isEqualTo(user.getEmail());
    }
}
