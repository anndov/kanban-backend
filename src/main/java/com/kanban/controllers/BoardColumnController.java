package com.kanban.controllers;

import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import com.kanban.service.BoardColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by david on 21-Dec-17.
 */
@RestController
@RequestMapping("rest/board-column")
public class BoardColumnController {

    @Autowired
    private BoardColumnService boardColumnService;
    @PutMapping()
    ResponseEntity<?> update(@RequestBody BoardColumn boardColumn) {
        boardColumnService.save(boardColumn);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }
}
