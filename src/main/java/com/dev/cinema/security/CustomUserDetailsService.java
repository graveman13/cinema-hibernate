package com.dev.cinema.security;

import com.dev.cinema.model.Role;
import com.dev.cinema.model.User;
import com.dev.cinema.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(email);
            builder.password(user.getPassword());
            builder.roles(convertRoleListToArray(user.getRoles()));
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }

    private String[] convertRoleListToArray(List<Role> list) {
        return list.stream().map(Role::getRoleName)
                .collect(Collectors.toList()).toArray(String[]::new);
    }
}
