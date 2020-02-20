package com.dev.cinema.controllers;

import com.dev.cinema.dto.CinemaHallRequestDto;
import com.dev.cinema.dto.CinemaHallResponseDto;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.service.CinemaHallService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinemaHalls")
public class CinemaHallController {
    @Autowired
    private CinemaHallService cinemaHallService;

    @GetMapping
    public List<CinemaHallResponseDto> getAll() {
        return cinemaHallService.getAll().stream()
                .map(this::convertCinemaHallToCinemaHallDtoResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public CinemaHallResponseDto add(@RequestBody CinemaHallRequestDto cinemaHallRequestDto) {
        return convertCinemaHallToCinemaHallDtoResponse(
                cinemaHallService.add(convertCHallRequestToCHall(cinemaHallRequestDto)));
    }

    private CinemaHallResponseDto convertCinemaHallToCinemaHallDtoResponse(CinemaHall cinemaHall) {
        CinemaHallResponseDto cinemaHallResponseDto = new CinemaHallResponseDto();
        cinemaHallResponseDto.setCapacity(cinemaHall.getCapacity());
        cinemaHallResponseDto.setDescription(cinemaHall.getDescription());
        return cinemaHallResponseDto;
    }

    private CinemaHall convertCHallRequestToCHall(CinemaHallRequestDto cinemaHallRequestDto) {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(cinemaHallRequestDto.getCapacity());
        cinemaHall.setDescription(cinemaHallRequestDto.getDescription());
        return cinemaHall;
    }
}
