package com.dev.cinema.dto;

import java.util.List;

public class OrderResponseDto {
    private Long orderId;
    private String email;
    private String firstName;
    private String lastName;
    private List<TicketsResponseDto> tickets;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<TicketsResponseDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketsResponseDto> tickets) {
        this.tickets = tickets;
    }
}
