package com.kanban.security.repository;

import com.kanban.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.boards b where b.id = :id and u.username like :username")
    List<User> findUsersByUsernameLikeAndBoardId(@Param("username") String username, @Param("id") Long id);

    @Query("SELECT u FROM User u JOIN u.boards b where b.id = :id and u.username like :username and u.enabled = true and u.validated = true")
    List<User> findUsersByUsernameLikeAndBoardIdAndEnabledAndValidated(@Param("username") String username, @Param("id") Long id);
}