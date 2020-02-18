package com.dev.cinema;

import com.dev.cinema.config.AppConfig;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext contex
                = new AnnotationConfigApplicationContext(AppConfig.class);
        Movie movie1 = new Movie();
        movie1.setTitle("Fast and Furious");
        Movie movie2 = new Movie();
        movie2.setTitle("Taxi");
        MovieService movieService = contex.getBean(MovieService.class);
        movie1 = movieService.add(movie1);
        movie2 = movieService.add(movie2);
        CinemaHallService cinemaHallService = contex.getBean(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(150);
        cinemaHall = cinemaHallService.add(cinemaHall);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setCinemaHall(cinemaHall);
        movieSession1.setMovie(movie1);
        movieSession1.setShowTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 30)));

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setCinemaHall(cinemaHall);
        movieSession2.setMovie(movie2);
        movieSession2.setShowTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(4, 10)));
        MovieSessionService movieSessionService = contex.getBean(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.findAvailableSessions(movie1.getId(), LocalDate.now())
                .forEach(System.out::println);
        movieSessionService.add(movieSession2);
        movieSessionService.findAvailableSessions(movie2.getId(), LocalDate.now())
                .forEach(System.out::println);

        movieService.getAll().forEach(System.out::println);
        AuthenticationService authenticationService = contex.getBean(AuthenticationService.class);
        authenticationService.register("login", "1");
        User user = authenticationService.login("login", "1");
        System.out.println(user);
        ShoppingCartService shoppingCartService = contex.getBean(ShoppingCartService.class);
        shoppingCartService.addSession(movieSession1, user);
        System.out.println(shoppingCartService.getByUser(user));

        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        OrderService orderService = contex.getBean(OrderService.class);
        orderService.completeOrder(shoppingCart.getTickets(), user);
        orderService.getOrderHistory(user).forEach(System.out::println);
    }
}
