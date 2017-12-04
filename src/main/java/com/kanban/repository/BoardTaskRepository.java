package com.kanban.repository;

import com.kanban.model.Board;
import com.kanban.model.BoardTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardTaskRepository extends JpaRepository<BoardTask, Long> {

    Page findByBoardColumnIdAndDeletedFalse(Long id, Pageable pageable);
    Page findByBoardIdAndDeletedFalse(Long id, Pageable pageable);
    List<BoardTask> findByBoardColumnIdAndDeletedFalse(Long id);
}
