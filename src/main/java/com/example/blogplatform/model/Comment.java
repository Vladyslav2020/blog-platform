package com.example.blogplatform.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String content;

    private Long likes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_by")
    private Comment replyBy;

    @JoinColumn(name = "publication_date")
    private LocalDateTime publicationDate;

    public Comment() {
    }

    public Comment(User user, Post post, String content, Long likes, Comment replyBy, LocalDateTime publicationDate) {
        this.user = user;
        this.post = post;
        this.content = content;
        this.likes = likes;
        this.replyBy = replyBy;
        this.publicationDate = publicationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Comment getReplyBy() {
        return replyBy;
    }

    public void setReplyBy(Comment replyBy) {
        this.replyBy = replyBy;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }
}
