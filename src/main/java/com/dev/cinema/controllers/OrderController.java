package com.dev.cinema.controllers;

import com.dev.cinema.dto.OrderRequestDto;
import com.dev.cinema.dto.OrderResponseDto;
import com.dev.cinema.dto.TicketsResponseDto;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/complete")
    public void complite(@RequestBody OrderRequestDto orderRequestDto) {
        orderService.completeOrder(
                shoppingCartService.getById(orderRequestDto.getShoppingCartId()).getTickets(),
                userService.getById(orderRequestDto.getUserId()));
    }

    @GetMapping("/")
    public List<OrderResponseDto> getAllOrdersByUser(Long userId) {
        List<Order> ordersHistory = orderService.getOrderHistory(userService.getById(userId));
        return ordersHistory.stream()
                .map(order -> convertOrderToOrderDtoResponse(order))
                .collect(Collectors.toList());
    }

    private OrderResponseDto convertOrderToOrderDtoResponse(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setEmail(order.getUser().getEmail());
        orderResponseDto.setFirstName(order.getUser().getFirstName());
        orderResponseDto.setLastName(order.getUser().getFirstName());
        orderResponseDto.setTickets(convertTicketToTicketDtoResponse(order));
        return orderResponseDto;
    }

    private List<TicketsResponseDto> convertTicketToTicketDtoResponse(Order order) {
        List<Ticket> tickets = order.getTickets();
        List<TicketsResponseDto> ticketsDto = new ArrayList<>();

        for (Ticket ticket : tickets) {
            TicketsResponseDto ticketsResponseDto = new TicketsResponseDto();
            ticketsResponseDto.setMovieSessionId(ticket.getMovieSession().getId());
            ticketsResponseDto.setCinemaHallId(ticket.getMovieSession().getCinemaHall().getId());
            ticketsResponseDto.setLocalDateTime(ticket.getMovieSession().getShowTime());
            ticketsResponseDto.setMovieName(ticket.getMovieSession().getMovie().getTitle());
            ticketsDto.add(ticketsResponseDto);
        }
        return ticketsDto;
    }
}
