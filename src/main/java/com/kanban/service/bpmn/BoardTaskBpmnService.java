package com.kanban.service.bpmn;

import com.kanban.model.BoardTask;
import com.kanban.model.BoardTaskColumns;
import com.kanban.service.BoardColumnService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardTaskBpmnService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private BoardColumnService boardColumnService;

    @Autowired
    private TaskService taskService;

    public BoardTask addTask(BoardTask boardTask) {

        String processInstanceId = runtimeService.startProcessInstanceByKey("board").getProcessInstanceId();

        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        task.setName(boardTask.getName());
        task.setDescription(boardTask.getDescription());
        task.setDueDate(boardTask.getDueDate());
        task.setAssignee(boardTask.getAssignee());
        taskService.saveTask(task);

        taskService.setVariableLocal(task.getId(), BoardTaskColumns.boardColumnId.name(), boardTask.getBoardColumnId());
        taskService.setVariableLocal(task.getId(), BoardTaskColumns.color.name(), boardTask.getColor());
        taskService.setVariableLocal(task.getId(), BoardTaskColumns.boardId.name(), boardTask.getBoardId());

        boardTask.setTaskId(task.getId());
        boardTask.setProcessInstanceId(processInstanceId);

        return boardTask;

    }

    public BoardTask updateTask(BoardTask boardTask) {

        Task task = taskService.createTaskQuery().taskId(boardTask.getTaskId()).singleResult();

        task.setName(boardTask.getName());
        task.setDescription(boardTask.getDescription());
        task.setDueDate(boardTask.getDueDate());
        task.setAssignee(boardTask.getAssignee());
        taskService.saveTask(task);

        taskService.setVariableLocal(task.getId(), BoardTaskColumns.boardColumnId.name(), boardTask.getBoardColumnId());
        taskService.setVariableLocal(task.getId(), BoardTaskColumns.color.name(), boardTask.getColor());
        taskService.setVariableLocal(task.getId(), BoardTaskColumns.boardId.name(), boardTask.getBoardId());

        boardTask.setTaskId(task.getId());

        return boardTask;
    }

    public List<BoardTask> findTasksByBoardId(Long id) {
        List<BoardTask> boardTasks = new ArrayList<>();
        List<Task> taskList = taskService.createTaskQuery().taskVariableValueEquals(BoardTaskColumns.boardId.name(), id).list();
        taskList.forEach(task -> {
            BoardTask boardTask = new BoardTask();
            boardTask.setName(task.getName());
            boardTask.setDescription(task.getDescription());
            boardTask.setDueDate(task.getDueDate());
            boardTask.setAssignee(task.getAssignee());

            boardTask.setBoardColumnId(Long.parseLong(String.valueOf(taskService.getVariableLocal(task.getId(), BoardTaskColumns.boardColumnId.name()))));
            boardTask.setColor(String.valueOf(taskService.getVariableLocal(task.getId(), BoardTaskColumns.color.name())));
            boardTask.setBoardId(Long.parseLong(String.valueOf(taskService.getVariableLocal(task.getId(), BoardTaskColumns.boardId.name()))));
            boardTasks.add(boardTask);

        });
        return boardTasks;
    }

    public List<BoardTask> findTasksByBoardColumnId(Long id) {
        List<BoardTask> boardTasks = new ArrayList<>();
        List<Task> taskList = taskService.createTaskQuery().taskVariableValueEquals(BoardTaskColumns.boardColumnId.name(), id).list();
        taskList.forEach(task -> {
            BoardTask boardTask = new BoardTask();
            boardTask.setName(task.getName());
            boardTask.setDescription(task.getDescription());
            boardTask.setDueDate(task.getDueDate());
            boardTask.setAssignee(task.getAssignee());

            boardTask.setBoardColumnId(Long.parseLong(String.valueOf(taskService.getVariableLocal(task.getId(), BoardTaskColumns.boardColumnId.name()))));
            boardTask.setColor(String.valueOf(taskService.getVariableLocal(task.getId(), BoardTaskColumns.color.name())));
            boardTask.setBoardId(Long.parseLong(String.valueOf(taskService.getVariableLocal(task.getId(), BoardTaskColumns.boardId.name()))));
            boardTasks.add(boardTask);

        });
        return boardTasks;
    }

    public Task findTaskByTaskId(String id) {
        return taskService.createTaskQuery().taskId(id).singleResult();
    }

    public void deleteTask(String id) {
        runtimeService.deleteProcessInstance(id, "User deleted");
    }

    public void completeTask(String id) {
        taskService.complete(id);
    }
}
