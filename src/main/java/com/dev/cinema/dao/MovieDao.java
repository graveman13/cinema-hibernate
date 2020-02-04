package com.dev.cinema.dao;

import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.Movie;

import java.util.List;

public interface MovieDao {
    Movie add(Movie movie);

    List<Movie> getAll() throws DataProcessingException;
}
