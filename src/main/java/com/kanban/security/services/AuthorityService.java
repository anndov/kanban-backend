package com.kanban.security.services;

import com.kanban.model.security.Authority;
import com.kanban.model.security.AuthorityName;
import com.kanban.security.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    public List<Authority> findByName(AuthorityName name) {
        return authorityRepository.findByName(name);
    }
}
