package com.example.blogplatform.repository;

import com.example.blogplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(Long id);
    boolean existsByEmail(String email);
    User findByEmail(String email);
    void deleteById(Long id);
}
