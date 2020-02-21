package com.dev.cinema.dto;

public class ShoppingCartRequestDto {
    private Long urerId;
    private Long movieSessionId;

    public Long getUrerId() {
        return urerId;
    }

    public void setUrerId(Long urerId) {
        this.urerId = urerId;
    }

    public Long getMovieSessionId() {
        return movieSessionId;
    }

    public void setMovieSessionId(Long movieSessionId) {
        this.movieSessionId = movieSessionId;
    }
}
