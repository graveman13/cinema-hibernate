package com.dev.cinema.service.impl;

import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.lib.Inject;
import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import com.dev.cinema.service.UserService;
import com.dev.cinema.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) {
        if (userService.findByEmail(email) == null
                || !HashUtil.hashPassword(password, userService.findByEmail(email).getSalt())
                .equals(userService.findByEmail(email).getPassword())) {
            throw new AuthenticationException("incorrect data");
        }
        return userService.findByEmail(email);
    }

    @Override
    public User register(String email, String password) {
        if (userService.findByEmail(email) == null) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        } else {
            throw new AuthenticationException("This email already exist, please login");
        }
    }
}
