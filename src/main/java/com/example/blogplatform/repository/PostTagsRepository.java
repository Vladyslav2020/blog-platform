package com.example.blogplatform.repository;

import com.example.blogplatform.model.PostTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagsRepository extends JpaRepository<PostTags, Long> {

}
