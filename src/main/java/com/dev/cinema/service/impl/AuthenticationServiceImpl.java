package com.dev.cinema.service.impl;

import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import com.dev.cinema.util.HashUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartService shoppingCartService;

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
    public User register(String email, String password,String firstName,String lastName) {
        if (userService.findByEmail(email) == null) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            User userWithId = userService.add(user);
            shoppingCartService.registerNewShoppingCart(userWithId);
            return userWithId;
        }
        throw new AuthenticationException("This email already exist, please login");
    }
}
