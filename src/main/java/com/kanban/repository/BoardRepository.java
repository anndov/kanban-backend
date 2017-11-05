package com.kanban.repository;

import com.kanban.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByParticipants_Username(String username);
}
