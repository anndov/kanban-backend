package com.kanban.repositories.sec;

import com.kanban.domain.sec.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);
}