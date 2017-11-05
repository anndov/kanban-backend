package com.kanban.controllers;

import com.kanban.model.Board;
import com.kanban.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @GetMapping(value = "/{id}")
    ResponseEntity<?> findById(@PathVariable Long id) {
        Board board = boardService.findOne(id);
        if (board == null)
            return new ResponseEntity<Object>("Board not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @GetMapping(value = "/username/{username}")
    ResponseEntity<List<Board>> findByParticipants_Username(@PathVariable String username) {
        List<Board> boards = boardService.findByParticipants_Username(username);
        return new ResponseEntity<List<Board>>(boards, HttpStatus.OK);
    }
}
