package com.dev.cinema.service;

import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;

public interface ShoppingCartService {

    ShoppingCart getById(Long id);

    ShoppingCart addSession(MovieSession movieSession, User user);

    ShoppingCart getByUser(User user);

    void registerNewShoppingCart(User user);

    void clean(ShoppingCart shoppingCart);
}
