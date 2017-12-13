package com.kanban.controllers;

import com.kanban.model.InviteToken;
import com.kanban.service.InviteTokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by david on 11-Dec-17.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(value = InviteTokenController.class, secure = false)
public class InviteTokenControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InviteTokenService inviteTokenService;

    @Test
    public void send_invite_token_test() throws Exception {
        this.mockMvc.perform(get("/rest/invite/email/mail.mail@mail.com/board-id/" + 11L))
                .andExpect(status().isOk());
    }

    @Test
    public void accept_invite() throws Exception {
        this.mockMvc.perform(get("/invitation/accept/" + "somestring"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void count_by_board_id() throws Exception {
        Mockito.when(this.inviteTokenService.countByBoardId(anyLong())).thenReturn(anyLong());
        this.mockMvc.perform(get("/rest/invite/count/board-id/" + 11L))
                .andExpect(status().isOk());
    }

    @Test
    public void find_by_board() throws Exception {
        Mockito.when(this.inviteTokenService.countByBoardId(anyLong())).thenReturn(anyLong());
        this.mockMvc.perform(get("/rest/invite/board-id/" + 11L))
                .andExpect(status().isOk());
    }

}
