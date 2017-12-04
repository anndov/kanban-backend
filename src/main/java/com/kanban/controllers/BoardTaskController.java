package com.kanban.controllers;

import com.kanban.model.BoardTask;
import com.kanban.service.BoardTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest/tasks")
public class BoardTaskController {

    @Autowired
    private BoardTaskService boardTaskService;

    @PostMapping
    ResponseEntity<?> addTask(@RequestBody BoardTask boardTask) {
        return new ResponseEntity<BoardTask>(boardTaskService.save(boardTask), HttpStatus.CREATED);
    }

    @GetMapping(value = "/board_column_id/{boardColumnId}")
    Page findTasksByBoardColumnId(@PathVariable Long boardColumnId) {
        return boardTaskService.findByBoardColumnIdAndDeletedFalse(boardColumnId);
    }

    @GetMapping(value = "/board_id/{boardId}")
    Page findTasksByBoardId(@PathVariable Long boardId) {
        return boardTaskService.findByBoardIdAndDeletedFalse(boardId);
    }

    @PutMapping
    ResponseEntity<?> updateTask(@RequestBody BoardTask boardTask) {
        return new ResponseEntity<BoardTask>(boardTaskService.save(boardTask), HttpStatus.OK);
    }

    @DeleteMapping(value = "/id/{id}")
    ResponseEntity<?> deleteTask(@PathVariable Long id) {
        boardTaskService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
