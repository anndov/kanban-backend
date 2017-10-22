package com.kanban.controllers;

import com.kanban.model.security.User;
import com.kanban.security.JwtUser;
import com.kanban.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

   /* @RequestMapping(method = RequestMethod.GET)
    Collection<User> findAll() {
        return userService.findAll();
    }*/

    @RequestMapping(method = RequestMethod.GET)
    Page findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userName}")
    User findByUsername(@PathVariable String userName) {
        return userService.findByUsername(userName);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> save(@RequestBody User user) {
        User entity = userService.save(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(entity, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity<?> update(@RequestBody User user) {
        User entity = userService.update(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(entity, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
