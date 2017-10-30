package com.kanban.service;

import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import com.kanban.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardColumnService boardColumnService;

    public Board save(Board board) {
        Set<BoardColumn> boardColumns = new HashSet<>();
        if(board.getBoardColumns() != null)
        board.getBoardColumns().forEach(boardColumn -> {
            System.out.println("boardColumn " + boardColumn.getName());
            boardColumns.add(boardColumnService.save(boardColumn));
        });
        board.setBoardColumns(boardColumns);
        return boardRepository.save(board);
    }
}
