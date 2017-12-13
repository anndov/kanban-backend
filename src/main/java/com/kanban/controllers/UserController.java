package com.kanban.controllers;

import com.kanban.security.model.User;
import com.kanban.security.services.UserService;
import com.kanban.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @GetMapping
    Page findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping(value = "/{userName}")
    User findByUsername(@PathVariable String userName) {
        return userService.findByUsername(userName);
    }

    @PostMapping
    ResponseEntity<?> save(@RequestBody User user) {
        User entity = userService.save(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(entity, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping
    ResponseEntity<?> update(@RequestBody User user) {
        User entity = userService.update(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(entity, responseHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/board-id/{boardId}")
    ResponseEntity<?> findUsersByBoardId(@PathVariable Long boardId) {
        return new ResponseEntity<>(boardService.findUsersByBoardId(boardId), HttpStatus.OK);
    }

    /*@GetMapping(value = "/username/{username}/board-id/{boardId}")
    ResponseEntity<?> findUsersByBoardId(@PathVariable String username, @PathVariable Long boardId) {
        return new ResponseEntity<>(userService.findUsersByUsernameLikeAndBoardId(username, boardId), HttpStatus.OK);
    }*/

    @GetMapping(value = "/username/{username}/board-id/{boardId}")
    ResponseEntity<?> findUsersByUsernameAndBoardId(@PathVariable String username, @PathVariable Long boardId) {
        return new ResponseEntity<>(userService.findUsersByUsernameLikeAndBoardIdAndEnabledAndValidated(username, boardId), HttpStatus.OK);
    }
}
