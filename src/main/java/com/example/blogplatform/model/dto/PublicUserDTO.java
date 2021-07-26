package com.example.blogplatform.model.dto;

import com.example.blogplatform.model.User;

import java.time.LocalDateTime;

public class PublicUserDTO {
    private Long id;
    private String userName;
    private String avatar;
    private String about;
    private String registrationDate;

    public PublicUserDTO(Long id, String username, String avatar, String about, String registrationDate) {
        this.id = id;
        this.userName = username;
        this.avatar = avatar;
        this.about = about;
        this.registrationDate = registrationDate;
    }

    public PublicUserDTO(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.avatar = user.getAvatar();
        this.about = user.getAbout();
        this.registrationDate = user.getRegistrationDate().toString();
    }

    public Long getId() {
        return id;
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
}
