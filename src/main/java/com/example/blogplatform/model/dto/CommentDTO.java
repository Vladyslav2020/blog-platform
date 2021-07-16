package com.example.blogplatform.model.dto;

import com.example.blogplatform.model.Post;
import com.example.blogplatform.model.User;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private Long likes;
    private Long replyBy;
    private String publicationDate;

    public CommentDTO(Long id, Long userId, Long postId, String content, Long likes, Long replyBy, String publicationDate) {
        this.id = id;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
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

    public Long getReplyBy() {
        return replyBy;
    }

    public void setReplyBy(Long replyBy) {
        this.replyBy = replyBy;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
}
