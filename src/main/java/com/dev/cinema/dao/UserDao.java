package com.dev.cinema.dao;

import com.dev.cinema.model.User;

import java.util.List;

public interface UserDao {
    User add(User user);

    User findByEmail(String email);

    User getById(Long id);

    List<User> getAll();
}
