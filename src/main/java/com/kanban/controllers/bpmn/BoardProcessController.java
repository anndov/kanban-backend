package com.kanban.controllers.bpmn;

import com.kanban.service.bpmn.BoardTaskBpmnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/process-instance")
public class BoardProcessController {

    /*@Autowired
    private BoardTaskBpmnService boardTaskBpmnService;

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> delete(@PathVariable String id) {
        boardTaskBpmnService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/
}
