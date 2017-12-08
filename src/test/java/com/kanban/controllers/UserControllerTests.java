package com.kanban.controllers;

import com.google.gson.Gson;
import com.kanban.security.model.User;
import com.kanban.security.services.UserService;
import com.kanban.service.BoardService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.theInstance;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BoardService boardService;

    public User getUser() {
        return new User("kermit", new BCryptPasswordEncoder().encode("12345"), "foo.har@gmail.com", true);
    }

    public Page<User> getUsersPage() {
        return new PageImpl<>(Arrays.asList(getUser(), getUser()));
    }

    public List<User> getUsers() {
        return new ArrayList<>(Arrays.asList(getUser(), getUser()));
    }

    @Test
    public void save_test() throws Exception {
        User user = getUser();
        User added = getUser();
        added.setId(11111L);

        Mockito.when(this.userService.save(any(User.class))).thenReturn(added);

        this.mockMvc.perform(post("/rest/users")
                .content(new Gson().toJson(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void update_test() throws Exception {
        User user = getUser();
        user.setId(1111L);

        User updated = getUser();
        updated.setId(1111L);
        updated.setUsername("updated");

        Mockito.when(this.userService.update(any(User.class))).thenReturn(updated);

        this.mockMvc.perform(put("/rest/users")
                .content(new Gson().toJson(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(updated.getUsername())));
    }

    @Test
    public void delete_test() throws Exception {
        this.mockMvc.perform(delete("/rest/users/" + anyLong()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void find_by_username_test() throws Exception {
        Mockito.when(userService.findByUsername(anyString())).thenReturn(getUser());
        this.mockMvc.perform(get("/rest/users/username/", anyString()))
                .andExpect(status().isOk());
    }

    @Test
    public void find_all_test() throws Exception {
        Mockito.when(userService.findAll(Mockito.mock(Pageable.class))).thenReturn(getUsersPage());

        this.mockMvc.perform(get("/rest/users/" + Mockito.mock(Pageable.class)))
                .andExpect(status().isOk());
    }

    @Test
    public void find_users_by_board_id_test() throws Exception {
        Mockito.when(this.boardService.findUsersByBoardId(anyLong())).thenReturn(getUsers());

        this.mockMvc.perform(get("/rest/users/board-id/" + 111L))
                .andExpect(status().isOk());
    }

    @Test
    public void find_users_by_board_id_and_username_test() throws Exception {
        Mockito.when(this.userService.findUsersByUsernameLikeAndBoardId(anyString(), anyLong())).thenReturn(getUsers());

        this.mockMvc.perform(get("/rest/users/username/" + "anystring" + "/board-id/" + 111L))
                .andExpect(status().isOk());
    }

}
