package com.kanban.service;

import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import com.kanban.repository.BoardRepository;
import com.kanban.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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
            boardColumns.add(boardColumnService.save(boardColumn));
        });
        board.setBoardColumns(boardColumns);
        return boardRepository.save(board);
    }

    public void deleteAllInBatch() {
        boardRepository.deleteAllInBatch();
    }

    public Board findOne(Long id) {
        return boardRepository.findOne(id);
    }

    public List<Board> findByParticipants_Username(String username) {
        return boardRepository.findByParticipants_Username(username);
    }

    public List<Board> findByParticipants_UsernameLike(String username) {
        return boardRepository.findByParticipants_UsernameLike(username);
    }

    public List<User> findUsersByBoardId(Long id) {
        return boardRepository.findUsersByBoardId(id);
    }

    public void delete(Long id) {
        boardRepository.delete(id);
    }

}
