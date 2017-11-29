package com.kanban.services;

import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import com.kanban.model.BoardTask;
import com.kanban.security.model.User;
import com.kanban.security.services.UserService;
import com.kanban.service.BoardService;
import com.kanban.service.bpmn.BoardTaskBpmnService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BoardTaskBpmnServiceTests {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardTaskBpmnService boardTaskBpmnService;

    @Autowired
    private UserService userService;

    private User user;

    private Board board;

    private BoardColumn backLog;
    private BoardColumn inProgress;

    @Before
    public void setup() {
        boardService.deleteAllInBatch();
        if(userService.findByUsername("kermit") != null)
            userService.delete(userService.findByUsername("kermit").getId());
        user = userService.save(new User("kermit", "12345", "kermit.board@gmail.com", true));

        board = new Board();
        board.setName("First board");
        board.setOwner(user);
        board.setParticipants(Collections.singleton(user));
        board.setBoardColumns(new HashSet<>(Arrays.asList(new BoardColumn("Backlog", 1), new BoardColumn("In progress", 2))));
        board = boardService.save(board);

        board.getBoardColumns().forEach(boardColumn -> {
            if (boardColumn.getName().equals("Backlog"))
                backLog = boardColumn;

            if (boardColumn.getName().equals("In progress"))
                inProgress = boardColumn;
        });

    }

    public void complete_task() {
        BoardTask boardTask = create();
        boardTask = boardTaskBpmnService.addTask(boardTask);
        boardTaskBpmnService.completeTask(boardTask.getTaskId());
        assertThat(boardTaskBpmnService.findTaskByTaskId(boardTask.getTaskId())).isEqualTo(null);
    }

    private BoardTask create() {
        BoardTask  bTask = new BoardTask();
        bTask.setName("Sample");
        bTask.setDescription("Sample Desc");
        bTask.setBoardColumnId(backLog.getId());
        bTask.setBoardId(board.getId());
        bTask.setColor("yellow");
        bTask.setDueDate(new Date());
        bTask.setAssignee("kermit");
        bTask.setOwner("kermit");

        return boardTaskBpmnService.addTask(bTask);
    }

    @Test
    public void delete_task() {
        boardTaskBpmnService.deleteTask(create().getProcessInstanceId());
    }

    @Test
    public void update_task() {
        BoardTask bTask = create();
        bTask.setName("Sample Updated");
        bTask.setDescription("Sample Desc Updated");
        bTask.setBoardColumnId(inProgress.getId());
        bTask.setBoardId(board.getId());
        bTask.setColor("green");
        bTask.setDueDate(null);
        bTask.setAssignee("kermit");
        bTask.setOwner("kermit");

        bTask = boardTaskBpmnService.updateTask(bTask);

        assertThat(bTask.getTaskId()).isNotEqualTo("");
        assertThat(bTask.getTaskId()).isNotEqualTo(null);
    }

    @Test
    public void add_task_and_variables() {
        BoardTask boardTask = new BoardTask();
        boardTask.setName("Real test");
        boardTask.setDescription("Real desc");
        boardTask.setBoardColumnId(backLog.getId());
        boardTask.setBoardId(board.getId());
        boardTask.setColor("red");
        boardTask.setDueDate(new Date());
        boardTask.setAssignee("kermit");
        boardTask.setOwner("kermit");

        boardTask = boardTaskBpmnService.addTask(boardTask);

        assertThat(boardTask.getTaskId()).isNotEqualTo("");
        assertThat(boardTask.getTaskId()).isNotEqualTo(null);

    }

    @Test
    public void find_tasks_by_board_id() {
        BoardTask boardTask = create();
        List<BoardTask> taskList = boardTaskBpmnService.findTasksByBoardId(board.getId());
        assertThat(taskList.size()).isNotEqualTo(0);
        boardTaskBpmnService.deleteTask(boardTask.getProcessInstanceId());
    }

    @Test
    public void find_tasks_by_board_column_id() {
        BoardTask boardTask = create();
        List<BoardTask> taskList = boardTaskBpmnService.findTasksByBoardColumnId(backLog.getId());
        assertThat(taskList.size()).isNotEqualTo(0);
        boardTaskBpmnService.deleteTask(boardTask.getProcessInstanceId());
    }

}
