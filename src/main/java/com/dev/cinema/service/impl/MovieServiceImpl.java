package com.dev.cinema.service.impl;

import com.dev.cinema.dao.MovieDao;
import com.dev.cinema.model.Movie;
import com.dev.cinema.service.MovieService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    @Override
    public Movie getById(Long id) {
        return movieDao.getById(id);
    }

    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
