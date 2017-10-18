package com.kanban.components;

import com.kanban.model.security.User;
import com.kanban.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class Loader implements ApplicationRunner {

    private UserService userService;

    @Autowired
    public Loader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        createDefaultUsers();
    }

    private void createDefaultUsers() {
        User admin = userService.findByUsername("admin") == null ? userService.save(new User("admin", "admin", "email@eamil.com", true)) : userService.findByUsername("admin");

    }

}
