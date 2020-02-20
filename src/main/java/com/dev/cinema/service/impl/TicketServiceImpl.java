package com.dev.cinema.service.impl;

import com.dev.cinema.model.Ticket;
import com.dev.cinema.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketService ticketService;

    @Override
    public Ticket add(Ticket ticket) {
        return ticketService.add(ticket);
    }

    @Override
    public Ticket getById(Long id) {
        return ticketService.getById(id);
    }
}
