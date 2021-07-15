package com.example.blogplatform.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post postId;

    private String content;

    private Long likes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_by")
    private User replyBy;

    @JoinColumn(name = "publication_date")
    private LocalDateTime publicationDate;

    public Comment() {
    }

    public Comment(Post postId, String content, Long likes, User replyBy, LocalDateTime publicationDate) {
        this.postId = postId;
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

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
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

    public User getReplyBy() {
        return replyBy;
    }

    public void setReplyBy(User replyBy) {
        this.replyBy = replyBy;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }
}
