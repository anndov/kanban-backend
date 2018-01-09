package com.kanban.controllers.integration;

import com.google.gson.Gson;
import com.kanban.model.request.PasswordChangeRequest;
import com.kanban.model.request.ProfileUpdateRequest;
import com.kanban.security.model.User;
import com.kanban.security.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by david on 21-Dec-17.
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@AutoConfigureTestDatabase
public class UserControllerIntegrationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void change_test() throws Exception {
        User user = new User();
        user.setEnabled(true);
        user.setEmail("emi.email@email.com");
        user.setPassword("12345");
        user = userService.save(user);

        PasswordChangeRequest request = new PasswordChangeRequest();
        request.setOldPassword("12345");
        request.setNewPassword("updated");

        this.mockMvc.perform(patch("/rest/users/password-change/" + user.getId())
                .content(new Gson().toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());;

        this.mockMvc.perform(patch("/rest/users/password-change/" + user.getId())
                .content(new Gson().toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void update_profile_test() throws Exception {
        User user = new User();
        user.setEnabled(true);
        user.setEmail("eami.email@email.com");
        user = userService.save(user);

        ProfileUpdateRequest request = new ProfileUpdateRequest();
        request.setFirstName("first name");
        request.setLastName("last name");

        this.mockMvc.perform(patch("/rest/users/update-profile/" + user.getId())
                .content(new Gson().toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        assertThat(userService.findByEmail(user.getEmail()).getFirstname()).isEqualTo(request.getFirstName());
        assertThat(userService.findByEmail(user.getEmail()).getLastname()).isEqualTo(request.getLastName());

    }

}
