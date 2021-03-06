package com.kanban.service;

import com.kanban.model.Board;
import com.kanban.model.BoardTask;
import com.kanban.repository.BoardTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BoardTaskService {

    @Autowired
    private BoardTaskRepository boardTaskRepository;

    public BoardTask save(BoardTask task) {
        if (task.getId() == null)
            task.setCreateDate(new Date());
        task.setUpdateDate(new Date());
        return boardTaskRepository.save(task);
    }

    public void delete(Long id) {
        BoardTask task = boardTaskRepository.findOne(id);
        task.setDeleted(true);
        boardTaskRepository.save(task);
    }

    public void deletePermanently(Long id) {
        boardTaskRepository.delete(id);
    }

    public Page findByBoardColumnIdAndDeletedFalse(Long boardColumnId) {
        return boardTaskRepository.findByBoardColumnIdAndDeletedFalse(boardColumnId, new PageRequest(0, 100));
    }

    public Page findByBoardIdAndDeletedFalse(Long boardId) {
        return boardTaskRepository.findByBoardIdAndDeletedFalse(boardId, new PageRequest(0, 500));
    }

    public List<BoardTask> findByBoardColumnId(Long boardColumnId) {
        return boardTaskRepository.findByBoardColumnIdAndDeletedFalse(boardColumnId);
    }

    public Page findAll() {
        return boardTaskRepository.findAll(new PageRequest(1, 100));
    }
}