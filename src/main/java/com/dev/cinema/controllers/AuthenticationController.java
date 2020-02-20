package com.dev.cinema.controllers;

import com.dev.cinema.dto.LoginRequestDto;
import com.dev.cinema.dto.RegisterRequestDto;
import com.dev.cinema.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public void login(@RequestBody LoginRequestDto loginRequestDto) {
        authenticationService.login(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword());
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequestDto registerRequestDto) {
        authenticationService.register(
                registerRequestDto.getEmail(),
                registerRequestDto.getPassword(),
                registerRequestDto.getFirstName(),
                registerRequestDto.getLastName());
    }
}
