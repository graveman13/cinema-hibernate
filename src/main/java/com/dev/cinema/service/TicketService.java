package com.dev.cinema.service;

import com.dev.cinema.model.Ticket;

public interface TicketService {
    Ticket add(Ticket ticket);

    Ticket getById(Long id);
}
