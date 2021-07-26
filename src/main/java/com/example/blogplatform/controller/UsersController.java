package com.example.blogplatform.controller;

import com.example.blogplatform.model.User;
import com.example.blogplatform.model.dto.PublicUserDTO;
import com.example.blogplatform.model.dto.UserDTO;
import com.example.blogplatform.security.AuthenticationUserHolder;
import com.example.blogplatform.security.Permission;
import com.example.blogplatform.service.UserService;
import javassist.NotFoundException;
import org.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public PublicUserDTO getUser(@PathVariable Long id) throws NotFoundException {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()){
            return new PublicUserDTO(user.get());
        }
        throw new NotFoundException("User is not found");
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody String body) throws AccessDeniedException {
        /*
            - userName
            - password
            - avatar
            - about
         */
        User user = AuthenticationUserHolder.getUser();
        JSONObject jsonObject = new JSONObject(body);
        String userName = jsonObject.has("userName") ? jsonObject.getString("userName"): null;
//        String email = jsonObject.has("email") ? jsonObject.getString("email"): null;
        String password = jsonObject.has("password") ? jsonObject.getString("password"): null;
        String avatar = jsonObject.has("avatar") ? jsonObject.getString("avatar"): null;
        String about = jsonObject.has("about") ? jsonObject.getString("about"): null;
        if (user.getId().equals(id)){
            return new UserDTO(userService.update(user, userName, password, avatar, about));
        }
        throw new AccessDeniedException("Access to user data is denied");
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('user:management', 'user:delete')")
    public void deleteAnyUser(@PathVariable Long id) throws AccessDeniedException {
        User user = AuthenticationUserHolder.getUser();
        userService.deleteUser(id);
        if (user.getRole().getPermissions().contains(Permission.USER_MANAGEMENT)){
            System.out.println("user has user:management permission");
        }
        if (user.getAuthorities().contains(Permission.USER_DELETE)){
            System.out.println("user has user:delete permission");
        }
        if (user.getId().equals(id)){
            if (userService.existsById(id));
                userService.deleteUser(id);
            return;
        }
        throw new AccessDeniedException("Access to deleting user data is denied");
    }
}
