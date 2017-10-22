package com.kanban.security.services;

import com.kanban.model.security.Authority;
import com.kanban.model.security.User;
import com.kanban.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityService authorityService;

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
        Set<Authority> authorities = new HashSet<Authority>();
        user.getAuthorities().forEach(authority -> {
            authorities.add(authorityService.findByName(authority.getName()).size() == 0 ?
                    authorityService.save(new Authority(authority.getName())) : authorityService.findByName(authority.getName()).get(0));
        });
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthorities(authorities);
        return userRepository.save(user);
    }

    public User update(User user) {
        Set<Authority> authorities = new HashSet<Authority>();
        user.getAuthorities().forEach(authority -> {
            authorities.add(authorityService.findByName(authority.getName()).size() == 0 ?
                    authorityService.save(new Authority(authority.getName())) : authorityService.findByName(authority.getName()).get(0));
        });
        user.setAuthorities(authorities);
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

    public void deleteAllInBatch() {
        userRepository.deleteAllInBatch();
    }

}
