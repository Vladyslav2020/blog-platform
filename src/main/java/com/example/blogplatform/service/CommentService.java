package com.example.blogplatform.service;

import com.example.blogplatform.model.Comment;
import com.example.blogplatform.model.Post;
import com.example.blogplatform.model.User;
import com.example.blogplatform.repository.CommentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getCommentsOfPost(Post post){
        return commentRepository.getAllByPost(post);
    }

    public List<Comment> getCommentsOfUser(User user){
        return commentRepository.getAllByUser(user);
    }

    public void add(User user, Post post, String content, User replyBy){
        commentRepository.save(new Comment(user, post, content, 0L, replyBy, LocalDateTime.now()));
    }

    public Optional<Comment> findById(Long id){
        return commentRepository.findById(id);
    }

    @Transactional
    public void updateComment(Comment comment, String content){
        if (content == null || !content.equals(comment.getContent())){
            comment.setContent(content);
        }
    }

    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }

    @Transactional
    public void putLike(Comment comment){
        comment.setLikes(comment.getLikes() + 1);
    }
}
