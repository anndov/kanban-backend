package com.kanban.security.repository;

import com.kanban.model.security.Authority;
import com.kanban.model.security.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    List<Authority> findByName(AuthorityName name);
}
