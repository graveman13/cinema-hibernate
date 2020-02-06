package com.dev.cinema.service;

import com.dev.cinema.model.User;

public interface AuthenticationService {
    User login(String email, String password);

    User register(String email, String password);
}
