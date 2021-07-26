package com.example.blogplatform.model.dto;

import com.example.blogplatform.model.User;

import java.time.LocalDateTime;

public class UserDTO {
    private Long id;
    private String userName;
    private String email;
    private String avatar;
    private String about;
    private String registrationDate;
    private boolean activated;

    public UserDTO(Long id, String username, String email, String avatar, String about, String registrationDate, boolean activated) {
        this.id = id;
        this.userName = username;
        this.email = email;
        this.avatar = avatar;
        this.about = about;
        this.registrationDate = registrationDate;
        this.activated = activated;
    }

    public UserDTO(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
        this.about = user.getAbout();
        this.registrationDate = user.getRegistrationDate().toString();
        this.activated = user.isActivated();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAbout() {
        return about;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
