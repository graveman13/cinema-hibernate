package com.dev.cinema.controllers;

import com.dev.cinema.dto.OrderRequestDto;
import com.dev.cinema.dto.OrderResponseDto;
import com.dev.cinema.dto.TicketsResponseDto;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public OrderResponseDto complite(@RequestBody OrderRequestDto orderRequestDto) {
        return convertOrderToOrderDtoResponse(orderService.completeOrder(
                shoppingCartService.getById(orderRequestDto.getShoppingCartId()).getTickets(),
                userService.getById(orderRequestDto.getUserId())));
    }

    @GetMapping("/{userId}")
    public List<OrderResponseDto> getAllOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrderHistory(userService.getById(userId)).stream()
                .map(this::convertOrderToOrderDtoResponse)
                .collect(Collectors.toList());
    }

    private OrderResponseDto convertOrderToOrderDtoResponse(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        User user = order.getUser();
        orderResponseDto.setEmail(user.getEmail());
        orderResponseDto.setFirstName(user.getFirstName());
        orderResponseDto.setLastName(user.getFirstName());
        orderResponseDto.setTickets(convertTicketToTicketDtoResponse(order));
        return orderResponseDto;
    }

    private List<TicketsResponseDto> convertTicketToTicketDtoResponse(Order order) {
        List<Ticket> tickets = order.getTickets();
        List<TicketsResponseDto> ticketsDto = new ArrayList<>();

        for (Ticket ticket : tickets) {
            TicketsResponseDto ticketsResponseDto = new TicketsResponseDto();
            MovieSession movieSession = ticket.getMovieSession();
            ticketsResponseDto.setMovieSessionId(movieSession.getId());
            ticketsResponseDto.setCinemaHallId(movieSession.getCinemaHall().getId());
            ticketsResponseDto.setLocalDateTime(movieSession.getShowTime());
            ticketsResponseDto.setMovieName(movieSession.getMovie().getTitle());
            ticketsDto.add(ticketsResponseDto);
        }
        return ticketsDto;
    }
}
