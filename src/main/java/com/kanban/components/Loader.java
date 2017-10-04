package com.kanban.components;

import com.kanban.domain.enums.Roles;
import com.kanban.services.sec.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class Loader implements ApplicationRunner {

    private CustomUserDetailsService userService;

    @Autowired
    public Loader(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        createDefaultUsers();
    }

    private void createDefaultUsers() {
        List<String> userRoles = new ArrayList<String>() {
        };
        userRoles.add(Roles.ROLE_USER.toString());

        if (!userService.userExists("user")) {
            userService.createUser("user", "user@user.com", "12345", userRoles, null, null);
        }

        List<String> adminRoles = new ArrayList<String>() {
        };
        adminRoles.add(Roles.ROLE_USER.toString());
        adminRoles.add(Roles.ROLE_ADMIN.toString());

        if (!userService.userExists("admin")) {
            userService.createUser("admin", "admin@admin.com", "12345", adminRoles, null, null);
        }

        /*if (!userService.userExists("david")) {
            userService.createUser("david", "admin@admin.com", "12345", adminRoles, null, null);
        }*/
    }

}
