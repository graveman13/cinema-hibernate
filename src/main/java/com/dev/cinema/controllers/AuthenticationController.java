package com.dev.cinema.controllers;

import com.dev.cinema.dto.LoginRequestDto;
import com.dev.cinema.dto.UserRegistrationDto;
import com.dev.cinema.dto.UserResponseDto;
import com.dev.cinema.model.User;
import com.dev.cinema.service.AuthenticationService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        User user = authenticationService.login(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword());
        return convertUserDtoToUserResponseDto(user);
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationDto registerRequestDto) {
        User user = authenticationService.register(
                registerRequestDto.getEmail(),
                registerRequestDto.getPassword(),
                registerRequestDto.getFirstName(),
                registerRequestDto.getLastName());
        return convertUserDtoToUserResponseDto(user);
    }

    private UserResponseDto convertUserDtoToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFirstName(user.getPassword());
        userResponseDto.setLastName(user.getLastName());
        return userResponseDto;
    }
}
