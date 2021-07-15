package com.example.blogplatform.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private String content;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<PostTags> tags;

    private Long likes;

    private Long views;

    @Column(name = "publication_date")
    private LocalDateTime publicationDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post() {
    }

    public Post(String title, String description, String content, List<PostTags> tags, Long likes, Long views, LocalDateTime publicationDate, User user) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.tags = tags;
        this.likes = likes;
        this.views = views;
        this.publicationDate = publicationDate;
        this.user = user;
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

    public List<PostTags> getTags() {
        return tags;
    }

    public void setTags(List<PostTags> tags) {
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

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
