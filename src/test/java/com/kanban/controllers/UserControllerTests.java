package com.kanban.controllers;

import com.kanban.security.model.User;
import com.kanban.security.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UserControllerTests extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    List<User> userList = new ArrayList<>();

    @Before
    public void setup() {
        userService.deleteAllInBatch();
        this.userList.add(userService.save(new User("david", new BCryptPasswordEncoder().encode("12345"), "david.har@gmail.com", true)));
        this.userList.add(userService.save(new User("gonzo", new BCryptPasswordEncoder().encode("12345"), "foo.har@gmail.com", true)));
    }

    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())/*
                .andExpect(jsonPath("$", hasSize(2)))*/;
    }

    @Test
    public void findByUsernameTest() throws Exception {
        mockMvc.perform(get("/users/david"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("david")));
    }

    @Test
    public void saveTest() throws Exception {
        String userJson = json(new User("kermit", new BCryptPasswordEncoder().encode("12345"), "foo.har@gmail.com", true));
        mockMvc.perform(post("/users")
                .contentType(contentType)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void updateTest() throws Exception {
        User user = new User("yaaa", new BCryptPasswordEncoder().encode("12345"), "david.har@gmail.com", true);
        user.setId(this.userList.get(0).getId());
        String userJson = json(user);
        mockMvc.perform(put("/users")
                .contentType(contentType)
                .content(userJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteTest() throws Exception {
        this.mockMvc.perform(delete("/users/" + this.userList.get(0).getId()))
                .andExpect(status().isOk());
    }
}
