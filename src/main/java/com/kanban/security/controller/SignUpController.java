package com.kanban.security.controller;

import com.kanban.security.SignupRequest;
import com.kanban.security.repository.VerificationTokenRepository;
import com.kanban.security.services.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("registration")
public class SignUpController {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private SignupService signupService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        signupService.signup(signupRequest);
        return ResponseEntity.ok("Confirmation email has sent. Please check your email.");
    }

    @RequestMapping(value = "/confirmation/{token}", method = RequestMethod.GET)
    ResponseEntity<?> confirmation(@PathVariable String token) {
        if (verificationTokenRepository.findByToken(token) != null) {
            signupService.confirmation(token);
            return ResponseEntity.ok("Success");
        }
        else
            return ResponseEntity.ok("Token is incorrect or verification date expired.");
    }
}
