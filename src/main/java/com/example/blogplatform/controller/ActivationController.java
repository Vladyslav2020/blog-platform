package com.example.blogplatform.controller;

import com.example.blogplatform.model.User;
import com.example.blogplatform.security.AuthenticationUserHolder;
import com.example.blogplatform.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/activate")
public class ActivationController {
    private final UserService userService;

    public ActivationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("{code}")
    @PreAuthorize("hasAuthority('user:update')")
    public void activateAccount(@PathVariable String code){
        User user = AuthenticationUserHolder.getUser();
        if (user.getActivationCode() != null && user.getActivationCode().equals(code)){
            userService.setActivation(user);
        }
    }
}
