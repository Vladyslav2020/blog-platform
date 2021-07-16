package com.example.blogplatform.model.dto;

import com.example.blogplatform.model.PostTags;
import com.example.blogplatform.model.Tag;
import com.example.blogplatform.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {
    private Long id;
    private String title;
    private String description;
    private String content;
    private List<Tag> tags;
    private Long likes;
    private Long views;
    private String publicationDate;
    private Long userId;

    public PostDTO(Long id, String title, String description, String content, List<Tag> tags, Long likes, Long views, String publicationDate, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.tags = tags;
        this.likes = likes;
        this.views = views;
        this.publicationDate = publicationDate;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
