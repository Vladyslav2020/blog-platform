package com.example.blogplatform.service;

import com.example.blogplatform.model.Tag;
import com.example.blogplatform.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public boolean existsByName(String name){
        return tagRepository.existsByName(name);
    }

    public void add(String name){
        Tag tag = new Tag(name);
        tagRepository.save(tag);
    }

    public Tag findByName(String name){
        return tagRepository.findByName(name);
    }
}
