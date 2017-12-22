package com.kanban.controllers;

import com.kanban.service.InviteTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by david on 11-Dec-17.
 */
@RestController
@RequestMapping
public class InviteTokenController {

    @Autowired
    private InviteTokenService inviteTokenService;

    @GetMapping(value = "/rest/invite/email/{email}/board-id/{boardId}")
    ResponseEntity<?> inviteMember(@PathVariable String email, @PathVariable Long boardId) {
        inviteTokenService.sendInviteToken(email, boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/invitation/accept/{token}")
    ResponseEntity<?> acceptInvite(@PathVariable String token) {
        boolean accepted = inviteTokenService.acceptInvite(token);
        if (accepted) {
            return ResponseEntity.ok("Success");
        }
        else {
            return new ResponseEntity<Object>("Date is expired", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/rest/invite/count/board-id/{boardId}")
    ResponseEntity<?> countInviteMembers(@PathVariable Long boardId) {
        return new ResponseEntity<>(inviteTokenService.countByBoardId(boardId), HttpStatus.OK);
    }

    @GetMapping(value = "/rest/invite/board-id/{boardId}")
    ResponseEntity<?> findInviteMembers(@PathVariable Long boardId) {
        return new ResponseEntity<>(inviteTokenService.findByBoard(boardId), HttpStatus.OK);
    }

}
