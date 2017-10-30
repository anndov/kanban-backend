package com.kanban.service;

import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import com.kanban.repository.BoardColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardColumnService {

    @Autowired
    private BoardColumnRepository boardColumnRepository;

    public List<BoardColumn> findByBoardOrderByColumnOrderAsc(Board board) {
        return boardColumnRepository.findByBoardOrderByColumnOrderAsc(board);
    }

    public BoardColumn save(BoardColumn boardColumn) {
        return boardColumnRepository.save(boardColumn);
    }
}
