package com.dev.cinema.service.impl;

import com.dev.cinema.dao.OrderDao;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(List<Ticket> tickets, User user) {
        Order order = new Order();
        order.setTickets(tickets);
        order.setUser(user);
        shoppingCartService.clean(shoppingCartService.getByUser(user));
        return orderDao.add(order);
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        return orderDao.getOrderHistory(user);
    }
}
