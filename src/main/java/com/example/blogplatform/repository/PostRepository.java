package com.example.blogplatform.repository;

import com.example.blogplatform.model.Post;
import com.example.blogplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> getPostById(Long id);
    List<Post> getAllByUser(User user);
    void deleteById(Long id);
}
