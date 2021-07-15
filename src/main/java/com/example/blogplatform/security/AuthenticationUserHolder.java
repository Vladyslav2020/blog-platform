package com.example.blogplatform.security;

import com.example.blogplatform.model.User;
import com.example.blogplatform.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUserHolder {
    private static UserService userService;

    public AuthenticationUserHolder(UserService userService) {
        AuthenticationUserHolder.userService = userService;
    }

    public static User getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }
}
