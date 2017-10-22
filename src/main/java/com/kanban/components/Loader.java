package com.kanban.components;

import com.kanban.security.model.Authority;
import com.kanban.security.model.AuthorityName;
import com.kanban.security.model.User;
import com.kanban.security.services.AuthorityService;
import com.kanban.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class Loader implements ApplicationRunner {

    private UserService userService;
    private AuthorityService authorityService;

    @Autowired
    public Loader(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        createDefaultUsers();
    }

    private void createDefaultUsers() {
        User admin;
        Authority authority;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (userService.findByUsername("admin") == null)
            admin = new User("admin", "admin", true);
        else
            admin = userService.findByUsername("admin");

        if (authorityService.findByName(AuthorityName.ROLE_ADMIN).size() == 0)
            authority = new Authority(AuthorityName.ROLE_ADMIN);
        else
            authority = authorityService.findByName(AuthorityName.ROLE_ADMIN).get(0);

        Set<Authority> authorities = new HashSet<Authority>();
        authorities.add(authorityService.save(authority));
        admin.setAuthorities(authorities);

        admin.setValidated(true);
        userService.save(admin);
    }

}
