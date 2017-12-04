package com.kanban.services;

import com.kanban.model.BoardTask;
import com.kanban.security.services.UserService;
import com.kanban.service.BoardService;
import com.kanban.service.BoardTaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.endsWith;
import static org.mockito.Mockito.mock;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BoardTaskServiceTests {

    @MockBean
    private BoardTaskService boardTaskService;

    private Page<BoardTask> boardTasks;

    @Before
    public void setUp() {
        BoardTask task = new BoardTask();
        task.setName("Name");
        task.setDescription("Desc");
        task.setDueDate(new Date());
        task.setAssignee("kermit");
        task.setBoardColumnId(23L);

        BoardTask task2 = new BoardTask();
        task2.setName("Name2");
        task2.setDescription("Desc2");
        task2.setDueDate(new Date());
        task2.setAssignee("kermit2");
        task2.setBoardColumnId(22L);
        boardTasks = new PageImpl<BoardTask>(Arrays.asList(task, task2));
    }

    @Test
    public void findTasksByBoardColumnId() {
        Mockito.when(boardTaskService.findByBoardColumnIdAndDeletedFalse(23L)).thenReturn(boardTasks);
        assertThat(boardTaskService.findByBoardColumnIdAndDeletedFalse(23L).getNumberOfElements()).isEqualTo(2);
    }
}
