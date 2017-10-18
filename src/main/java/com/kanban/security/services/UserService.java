package com.kanban.security.services;

import com.kanban.model.security.User;
import com.kanban.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Page findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Long count() {
        return userRepository.count();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

    public void deleteAllInBatch() {
        userRepository.deleteAllInBatch();
    }

}
