package com.kanban.service.camunda;

import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import com.kanban.model.request.TaskRequest;
import com.kanban.service.BoardColumnService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BoardUserTaskService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private BoardColumnService boardColumnService;

    @Autowired
    private TaskService taskService;

    public void startBoardProcess(TaskRequest taskRequest) {

        ProcessInstance processInstance = runtimeService.startProcessInstanceById("baord");

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();

        taskService.setVariable("boardColumnId", taskRequest.getBoardColumnId().toString(), task.getId());
    }


}
