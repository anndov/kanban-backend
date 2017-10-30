package com.kanban.repository;

import com.kanban.model.Board;
import com.kanban.model.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {

    List<BoardColumn> findByBoardOrderByColumnOrderAsc(Board board);
}
