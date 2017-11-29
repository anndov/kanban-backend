package com.kanban.repository;

import com.kanban.model.Board;
import com.kanban.model.BoardTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardTaskRepository extends JpaRepository<BoardTask, Long> {

    Page findAllByBoardIdAndIsDeleted(Long id, boolean isDeleted, Pageable pageable);
}
