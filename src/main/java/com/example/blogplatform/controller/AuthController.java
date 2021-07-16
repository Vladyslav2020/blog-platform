package com.example.blogplatform.controller;

import com.example.blogplatform.model.User;
import com.example.blogplatform.model.dto.UserDTO;
import com.example.blogplatform.security.AuthenticationUserHolder;
import com.example.blogplatform.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register(@RequestBody String body){
        /*
            - userName
            - email
            - password
         */
        JSONObject jsonObject = new JSONObject(body);
        String userName = jsonObject.getString("userName");
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        userService.add(userName, email, password);
    }

    @PostMapping("/login")
    public UserDTO login(){
        /*
            - email
            - password
         */
        User user = AuthenticationUserHolder.getUser();
        userService.sendMessage(user);
        return new UserDTO(user.getId(), user.getUserName(), user.getEmail(), user.getAvatar(), user.getAbout(), user.getRegistrationDate().toString(), user.isActivated());
    }
}
