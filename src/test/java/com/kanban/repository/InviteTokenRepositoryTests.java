package com.kanban.repository;

import com.kanban.model.Board;
import com.kanban.model.InviteToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InviteTokenRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InviteTokenRepository inviteTokenRepository;

    @Test
    public void find_by_token_and_accepted() {
        InviteToken inviteToken = new InviteToken();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        inviteToken.setExpireDate(calendar.getTime());
        inviteToken.setToken(UUID.randomUUID().toString());
        inviteToken.setAccepted(false);

        entityManager.persist(inviteToken);
        entityManager.flush();

        InviteToken foun = this.inviteTokenRepository.findByTokenAndIsAcceptedFalse(inviteToken.getToken());

        assertThat(foun.getToken()).isEqualTo(inviteToken.getToken());
    }

    @Test
    public void count_by_board_id() {
        Board board = new Board();
        board.setName("name");
        entityManager.persist(board);
        entityManager.flush();

        InviteToken inviteToken = new InviteToken();
        inviteToken.setBoard(board);
        inviteToken.setAccepted(false);
        entityManager.persist(inviteToken);
        entityManager.flush();

        assertThat(inviteTokenRepository.countByBoard_IdAndIsAcceptedFalse(board.getId())).isEqualTo(1L);
    }

    @Test
    public void find_by_board_id_and_expire_date_greater() {
        Board board = new Board();
        board.setName("name");
        entityManager.persist(board);
        entityManager.flush();

        InviteToken inviteToken = new InviteToken();
        inviteToken.setBoard(board);
        inviteToken.setAccepted(false);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        inviteToken.setExpireDate(calendar.getTime());
        entityManager.persist(inviteToken);
        entityManager.flush();

        assertThat(inviteTokenRepository.findByBoard_IdAndExpireDateGreaterThan(board.getId(), new Date()).size()).isEqualTo(1);
    }
}
