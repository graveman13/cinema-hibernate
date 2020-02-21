package com.dev.cinema.dao;

import com.dev.cinema.model.MovieSession;

import java.time.LocalDate;
import java.util.List;

public interface MovieSessionDao {
    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);

    MovieSession getById(Long id);

    MovieSession add(MovieSession movieSession);
}
