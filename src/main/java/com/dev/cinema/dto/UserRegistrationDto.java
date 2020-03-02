package com.dev.cinema.dto;

import com.dev.cinema.anotations.EmailConstraint;
import com.dev.cinema.anotations.PasswordConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordConstraint
public class UserRegistrationDto {
    @NotNull
    @EmailConstraint
    private String email;
    @NotNull
    @Size(min = 4)
    private String password;
    @NotNull
    private String repeatPassword;
    @NotNull
    @Size(min = 2)
    private String firstName;
    @NotNull
    @Size(min = 2)
    private String lastName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
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
}
