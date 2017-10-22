package com.kanban.security.repository;

import com.kanban.security.model.Authority;
import com.kanban.security.model.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    List<Authority> findByName(AuthorityName name);
}
