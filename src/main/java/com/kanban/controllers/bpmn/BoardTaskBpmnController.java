package com.kanban.controllers.bpmn;

import com.kanban.model.BoardTask;
import com.kanban.service.bpmn.BoardTaskBpmnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/boardtasks")
public class BoardTaskBpmnController {

    /*@Autowired
    private BoardTaskBpmnService boardTaskBpmnService;

    @PostMapping
    ResponseEntity<?> save(@RequestBody BoardTask boardTask) {
        BoardTask bTask = boardTaskBpmnService.addTask(boardTask);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(bTask, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping
    ResponseEntity<?> update(@RequestBody BoardTask boardTask) {
        BoardTask bTask = boardTaskBpmnService.updateTask(boardTask);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(bTask, httpHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/boardColumnId/{boardColumnId}")
    ResponseEntity<List<BoardTask>> findTasksByBoardColumnId(@PathVariable Long boardColumnId) {
        List<BoardTask> boardTasks = boardTaskBpmnService.findTasksByBoardColumnId(boardColumnId);
        return new ResponseEntity<List<BoardTask>>(boardTasks, HttpStatus.OK);
    }

    @GetMapping(value = "/boardId/{boardId}")
    ResponseEntity<List<BoardTask>> findTasksByBoardId(@PathVariable Long boardId) {
        List<BoardTask> boardTasks = boardTaskBpmnService.findTasksByBoardId(boardId);
        return new ResponseEntity<List<BoardTask>>(boardTasks, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/complete")
    ResponseEntity<?> completeTask(@PathVariable String id) {
        boardTaskBpmnService.completeTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/
}
