package com.dev.cinema.controllers;

import com.dev.cinema.model.Role;
import com.dev.cinema.model.User;
import com.dev.cinema.service.RoleService;
import com.dev.cinema.service.UserService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @PostConstruct
    private void postConstruct() {
        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");
        roleService.add(adminRole);

        User userAdmin = new User();
        userAdmin.setEmail("admin@mail.com");
        userAdmin.setFirstName("Admin");
        userAdmin.setLastName("Admin");
        userAdmin.setPassword("1234");
        userAdmin.getRoles().add(adminRole);
        userService.add(userAdmin);

        Role userRole = new Role();
        userRole.setRoleName("USER");
        roleService.add(userRole);

        User userUser = new User();
        userUser.setEmail("user@mail.com");
        userUser.setPassword("1234");
        userUser.setFirstName("User");
        userUser.setLastName("User");
        userUser.getRoles().add(userRole);
        userService.add(userUser);
    }
}
