package com.example.blogplatform.service;

import com.example.blogplatform.model.Post;
import com.example.blogplatform.model.PostTags;
import com.example.blogplatform.model.Tag;
import com.example.blogplatform.repository.PostTagsRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PostTagsService {
    private final PostTagsRepository postTagsRepository;

    public PostTagsService(PostTagsRepository postTagsRepository) {
        this.postTagsRepository = postTagsRepository;
    }

    @Transactional
    public void deleteAllByPost(Post post){
        postTagsRepository.deleteAllByPost(post);
    }

    public void add(Post post, Tag tag){
        PostTags postTags = new PostTags(post, tag);
        postTagsRepository.save(postTags);
    }
}
