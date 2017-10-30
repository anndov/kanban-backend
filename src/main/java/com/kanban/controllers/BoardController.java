package com.kanban.controllers;

import com.kanban.model.Board;
import com.kanban.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by david on 29-Oct-17.
 */
@RequestMapping("boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping
    ResponseEntity<?> save(@RequestBody  Board board) {
        Board entity = boardService.save(board);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(entity, responseHeaders, HttpStatus.CREATED);
    }
}
