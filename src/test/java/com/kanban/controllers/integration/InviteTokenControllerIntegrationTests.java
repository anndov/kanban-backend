package com.kanban.controllers.integration;

import com.kanban.BackendApplication;
import com.kanban.model.Board;
import com.kanban.model.InviteToken;
import com.kanban.service.BoardService;
import com.kanban.service.InviteTokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by david on 11-Dec-17.
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class InviteTokenControllerIntegrationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BoardService boardService;

    @Autowired
    private InviteTokenService inviteTokenService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void send_invite_token_test() throws Exception {
        Board board = new Board();
        board.setName("Name");
        board = this.boardService.save(board);
        this.mockMvc.perform(get("/rest/invite/email/email.com@email.com/board-id/" + board.getId()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void accept_invite_test() throws Exception {
        Board board = new Board();
        board.setName("Name");
        board = this.boardService.save(board);
        InviteToken inviteToken = inviteTokenService.sendInviteToken("email.com@email.com", board.getId());
        this.mockMvc.perform(get("/invitation/accept/" + inviteToken.getToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void count_invite_token_test() throws Exception {
        Board board = new Board();
        board.setName("Name");
        board = this.boardService.save(board);
        inviteTokenService.sendInviteToken("email.com@email.com", board.getId());
        MvcResult mvcResult = this.mockMvc.perform(get("/rest/invite/count/board-id/" + board.getId()))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("1");
    }

    @Test
    public void find_invite_members_test() throws Exception {
        Board board = new Board();
        board.setName("Name");
        board = this.boardService.save(board);
        inviteTokenService.sendInviteToken("email.com@email.com", board.getId());
        this.mockMvc.perform(get("/rest/invite/board-id/" + board.getId()))
                .andExpect(status().isOk());
    }
}
