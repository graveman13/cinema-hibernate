package com.dev.cinema.dto;

import java.util.List;

public class ShoppingCartResponseDto {
    private String email;
    private String firstName;
    private String lastName;
    private List<MovieSessionResponseDto> movieSessions;

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

    public List<MovieSessionResponseDto> getMovieSessions() {
        return movieSessions;
    }

    public void setMovieSessions(List<MovieSessionResponseDto> movieSessions) {
        this.movieSessions = movieSessions;
    }
}
