package com.dev.cinema.controllers;

import com.dev.cinema.dto.MovieSessionResponseDto;
import com.dev.cinema.dto.ShoppingCartRequestDto;
import com.dev.cinema.dto.ShoppingCartResponseDto;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppingcarts")
public class ShoppingCartController {
    @Autowired
    private UserService userService;
    @Autowired
    private MovieSessionService movieSessionService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/addmoviesession")
    public void add(@RequestBody ShoppingCartRequestDto shoppingCartRequestDto) {
        shoppingCartService.addSession(
                movieSessionService.getById(shoppingCartRequestDto.getMovieSessionId()),
                userService.getById(shoppingCartRequestDto.getUrerId()));
    }

    @GetMapping("/byuser")
    public ShoppingCartResponseDto get(Long userId) {
        User user = userService.getById(userId);
        return convertScToCResponseDto(shoppingCartService.getByUser(user), user);
    }

    private ShoppingCartResponseDto convertScToCResponseDto(ShoppingCart shoppingCart, User user) {
        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
        shoppingCartResponseDto.setEmail(user.getEmail());
        shoppingCartResponseDto.setFirstName(user.getFirstName());
        shoppingCartResponseDto.setLastName(user.getLastName());
        shoppingCartResponseDto.setMovieSessions(shoppingCart.getTickets()
                .stream().map(this::convertTicketToMovieSessionResponseDto)
                .collect(Collectors.toList()));
        return shoppingCartResponseDto;
    }

    private MovieSessionResponseDto convertTicketToMovieSessionResponseDto(Ticket ticket) {
        MovieSession movieSession = movieSessionService.getById(ticket.getMovieSession().getId());
        MovieSessionResponseDto movieSessionResponseDto = new MovieSessionResponseDto();
        movieSessionResponseDto.setCinemaHallId(movieSession.getCinemaHall().getId());
        movieSessionResponseDto.setMovieTitle(movieSession.getMovie().getTitle());
        movieSessionResponseDto.setShowTime(movieSession.getShowTime());
        return movieSessionResponseDto;
    }
}
