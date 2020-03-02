package com.dev.cinema.service.impl;

import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import com.dev.cinema.service.RoleService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User login(String email, String password) {
        User user = userService.findByEmail(email);
        if (user == null
                || !user.getPassword().equals(passwordEncoder.encode(password))) {
            throw new AuthenticationException("incorrect data");
        }
        return user;
    }

    @Override
    public User register(String email, String password, String firstName, String lastName) {
        if (userService.findByEmail(email) == null) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.getRoles().add(roleService.getRoleName("USER"));
            User userWithId = userService.add(user);
            shoppingCartService.registerNewShoppingCart(userWithId);
            return userWithId;
        }
        throw new AuthenticationException("This email already exist, please login");
    }
}
