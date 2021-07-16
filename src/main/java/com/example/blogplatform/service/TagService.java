package com.example.blogplatform.service;

import com.example.blogplatform.model.Tag;
import com.example.blogplatform.repository.TagRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public boolean existsByName(String name){
        return tagRepository.existsByName(name);
    }

    public boolean existsById(Long id){
        return tagRepository.existsById(id);
    }

    public void add(String name){
        Tag tag = new Tag(name);
        tagRepository.save(tag);
    }

    public Tag findByName(String name){
        return tagRepository.findByName(name);
    }

    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }

    public Optional<Tag> findById(Long id){
        return tagRepository.findById(id);
    }

    @Transactional
    public void updateTag(Tag tag, String name){
        if (name != null && !tag.getName().equals(name))
            tag.setName(name);
    }

    @Transactional
    public void deleteTag(Long id){
        tagRepository.deleteById(id);
    }
}
