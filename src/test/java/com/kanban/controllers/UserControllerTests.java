package com.kanban.controllers;

import com.kanban.model.security.User;
import com.kanban.security.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserControllerTests {

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype());

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    List<User> userList = new ArrayList<>();

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);
    }

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
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
        this.mockMvc.perform(post("/users")
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

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
