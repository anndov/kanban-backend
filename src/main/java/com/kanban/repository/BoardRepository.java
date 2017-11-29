package com.kanban.repository;

import com.kanban.model.Board;
import com.kanban.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByParticipants_Username(String username);

    List<Board> findByParticipants_UsernameLike(String username);


    @Query("SELECT b.participants FROM Board b WHERE b.id = :id")
    List<User> findUsersByBoardId(@Param("id") Long id);
}
