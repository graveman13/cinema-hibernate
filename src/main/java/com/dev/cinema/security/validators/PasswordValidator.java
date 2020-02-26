package com.dev.cinema.security.validators;

import com.dev.cinema.anotations.PasswordConstraint;
import com.dev.cinema.dto.UserRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator
        implements ConstraintValidator<PasswordConstraint, UserRegistrationDto> {

    @Override
    public boolean isValid(UserRegistrationDto userRegister, ConstraintValidatorContext valid) {

        return userRegister.getPassword().equals(userRegister.getRepeatPassword());
    }
}
