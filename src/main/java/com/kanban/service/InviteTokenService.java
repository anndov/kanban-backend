package com.kanban.service;

import com.kanban.model.Board;
import com.kanban.model.InviteToken;
import com.kanban.repository.BoardRepository;
import com.kanban.repository.InviteTokenRepository;
import com.kanban.security.model.User;
import com.kanban.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by david on 10-Dec-17.
 */
@Service
public class InviteTokenService {

    @Autowired
    private InviteTokenRepository inviteTokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private JavaMailSender mailSender;

    public InviteToken sendInviteToken(String email, Long boardId) {
        User user = userService.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setEnabled(false);
            user.setValidated(false);
            user.setEmail(email);
            user = userService.save(user);
        }

        Board board = boardService.findOne(boardId);

        InviteToken inviteToken = new InviteToken();
        inviteToken.setToken(UUID.randomUUID().toString());
        inviteToken.setUser(user);
        inviteToken.setBoard(board);
        inviteToken.setAccepted(false);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        inviteToken.setExpireDate(calendar.getTime());

        inviteTokenRepository.save(inviteToken);

        String recipientAddress = user.getEmail();
        String subject = "Inivitation";
        String confirmationUrl = "http://localhost:8080/invitation/accept/" + inviteToken.getToken();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipientAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(confirmationUrl);
        mailSender.send(simpleMailMessage);

        return inviteToken;
    }

    public boolean acceptInvite(String token) {
        InviteToken inviteToken = inviteTokenRepository.findByTokenAndIsAcceptedFalse(token);
        if(inviteToken == null) {
            return false;
        }

        else {
            Board board = inviteToken.getBoard();
            board.setParticipants(Collections.singleton(inviteToken.getUser()));
            inviteToken.setAccepted(true);

            inviteTokenRepository.save(inviteToken);

            /*try {
                boardService.save(board);
            }
            catch (Exception e) {
                System.out.println("error message: " + e.getMessage());
            }*/

            return true;
        }
    }

    public Long countByBoardId(Long boardId) {
        return inviteTokenRepository.countByBoard_IdAndIsAcceptedFalse(boardId);
    }

    public List<InviteToken> findByBoard(Long boardId) {
        return inviteTokenRepository.findByBoard_IdAndExpireDateGreaterThan(boardId, new Date());
    }

}
