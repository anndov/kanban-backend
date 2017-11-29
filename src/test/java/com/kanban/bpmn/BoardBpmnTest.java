package com.kanban.bpmn;

import com.kanban.model.BoardTaskColumns;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.camunda.bpm.extension.mockito.CamundaMockito.autoMock;

@Deployment(resources = "bpmn/board.bpmn")
public class BoardBpmnTest extends AbstractProcessEngineRuleTest {

    @Test
    public void task_test() {
        autoMock("bpmn/board.bpmn");

        final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("board");

        assertThat(processInstance).isWaitingAt("task");

        Task task = taskService().createTaskQuery().singleResult();

        task.setName("Some work");
        task.setDescription("Some description");
        task.setAssignee("board");

        taskService().saveTask(task);

        runtimeService().setVariableLocal(executionQuery().singleResult().getId(), BoardTaskColumns.boardColumnId.name(), 111L);
        runtimeService().setVariableLocal(executionQuery().singleResult().getId(), BoardTaskColumns.color.name(), "red");

        assertThat(runtimeService().getVariableLocal(executionQuery().singleResult().getId(), BoardTaskColumns.boardColumnId.name())).isEqualTo(111L);
        assertThat(runtimeService().getVariableLocal(executionQuery().singleResult().getId(), BoardTaskColumns.color.name())).isEqualTo("red");

        complete(task);

    }

    @Test
    public void start_and_finish_process() {
        autoMock("bpmn/board.bpmn");

        final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("board");

        assertThat(processInstance).isWaitingAt("task");

        complete(task());

        assertThat(processInstance).isEnded();
    }


}
