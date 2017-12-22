package com.kanban.security.services;

import com.kanban.security.SignupRequest;
import com.kanban.security.model.User;
import com.kanban.security.model.VerificationToken;
import com.kanban.security.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SignupService {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void signup(SignupRequest signupRequest) {
        User user = userService.findByEmail(signupRequest.getEmail());
        if ( user == null)
            user = new User(signupRequest.getUsername(), signupRequest.getPassword(), signupRequest.getEmail(), false);

        user.setUsername(signupRequest.getUsername());
        user.setPassword(signupRequest.getPassword());
        userService.save(user);

        VerificationToken verificationToken = verificationTokenRepository.findByUser(user) == null ? new VerificationToken() : verificationTokenRepository.findByUser(user);
        verificationToken.setUser(user);
        verificationToken.setToken(UUID.randomUUID().toString());

        verificationTokenRepository.save(verificationToken);
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = "http://localhost:8080/registration/confirmation/" + verificationToken.getToken();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(confirmationUrl);
        mailSender.send(email);
    }

    public void confirmation(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        User user = verificationToken.getUser();
        user.setValidated(true);
        user.setEnabled(true);
        userService.update(user);
    }
}
