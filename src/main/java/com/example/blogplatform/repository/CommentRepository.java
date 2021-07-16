package com.example.blogplatform.repository;

import com.example.blogplatform.model.Comment;
import com.example.blogplatform.model.Post;
import com.example.blogplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByPost(Post post);
    List<Comment> getAllByUser(User user);
}
