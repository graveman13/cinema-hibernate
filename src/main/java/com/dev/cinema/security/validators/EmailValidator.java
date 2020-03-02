package com.dev.cinema.security.validators;

import com.dev.cinema.anotations.EmailConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext ctx) {
        return contactField != null && contactField.matches(
                "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$")
                && (contactField.length() > 5);
    }
}
