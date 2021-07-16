package com.example.blogplatform.controller;

import com.example.blogplatform.model.Tag;
import com.example.blogplatform.service.TagService;
import org.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getAllTags(){
        return tagService.getAllTags();
    }

    @GetMapping("{id}")
    public Tag getTagById(@PathVariable Long id){
        Optional<Tag> optionalTag = tagService.findById(id);
        if (optionalTag.isPresent()){
            return optionalTag.get();
        }
        return null;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('tag:create')")
    public void addTag(@RequestBody String body){
        /*
            - name
         */
        JSONObject jsonObject = new JSONObject(body);
        String name = jsonObject.getString("name");
        tagService.add(name);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('tag:management')")
    public void updateTag(@PathVariable Long id, @RequestBody String body){
        /*
            - ?name
         */
        JSONObject jsonObject = new JSONObject(body);
        String name = jsonObject.has("name")? jsonObject.getString("name"): null;
        Optional<Tag> optionalTag = tagService.findById(id);
        if (optionalTag.isPresent()){
            tagService.updateTag(optionalTag.get(), name);
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('tag:management')")
    public void deleteTag(@PathVariable Long id){
        if (tagService.existsById(id))
            tagService.deleteTag(id);
    }
}
