package com.example.blogplatform.controller;

import com.example.blogplatform.model.Post;
import com.example.blogplatform.model.PostTags;
import com.example.blogplatform.model.Tag;
import com.example.blogplatform.model.User;
import com.example.blogplatform.model.dto.PostDTO;
import com.example.blogplatform.security.AuthenticationUserHolder;
import com.example.blogplatform.service.PostService;
import com.example.blogplatform.service.PostTagsService;
import com.example.blogplatform.service.TagService;
import javassist.NotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final TagService tagService;
    private final PostTagsService postTagsService;

    public PostController(PostService postService, TagService tagService, PostTagsService postTagsService) {
        this.postService = postService;
        this.tagService = tagService;
        this.postTagsService = postTagsService;
    }

    @GetMapping("{id}")
    public PostDTO getPost(@PathVariable Long id) throws NotFoundException {
        Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            return new PostDTO(post.getId(),
                    post.getTitle(),
                    post.getDescription(),
                    post.getContent(),
                    post.getTags().stream().map(PostTags::getTag).collect(Collectors.toList()),
                    post.getLikes(),
                    post.getViews(), post.getPublicationDate().toString(),
                    post.getUser().getId()
            );
        }
        return null;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('post:read')")
    public List<PostDTO> getAllUserPosts(@RequestParam(name = "user_id") Long userId){
        User user = AuthenticationUserHolder.getUser();
        if (user.getId().equals(userId)){
            return postService.getAllUserPosts(user).stream().map(item -> new PostDTO(item.getId(),
                    item.getTitle(),
                    item.getDescription(),
                    item.getContent(),
                    item.getTags().stream().map(elem -> elem.getTag()).collect(Collectors.toList()),
                    item.getLikes(),
                    item.getViews(),
                    item.getPublicationDate().toString(),
                    item.getUser().getId()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('post:create')")
    public void publishNewPost(@RequestParam(name = "user_id") Long userId, @RequestBody String body){
        /*
            - title
            - description
            - content
            - tags
                - tag
         */
        User user = AuthenticationUserHolder.getUser();
        JSONObject jsonObject = new JSONObject(body);
        String title = jsonObject.getString("title");
        String description = jsonObject.getString("description");
        String content = jsonObject.getString("content");
        List<String> tagList = new ArrayList<>();
        JSONArray tags = jsonObject.getJSONArray("tags");
        for (int i = 0; i < tags.length(); i++){
            tagList.add(tags.getJSONObject(i).getString("name"));
        }
        for (int i = 0; i < tagList.size(); i++){
            if (!tagService.existsByName(tagList.get(i))){
                tagService.add(tagList.get(i));
            }
        }
        Post post = postService.addPost(user, title, description, content, 0L, 0L);
        for (int i = 0; i < tagList.size(); i++){
            Tag tag = tagService.findByName(tagList.get(i));
            postTagsService.add(post, tag);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('post:update')")
    public void updatePost(@RequestParam(name = "user_id") Long userId, @RequestParam Long id, @RequestBody String body) throws NotFoundException {
        /*
            - title
            - description
            - content
         */
        User user = AuthenticationUserHolder.getUser();
        Optional<Post> optionalPost = postService.findById(id);
        JSONObject jsonObject = new JSONObject(body);
        String title = jsonObject.has("title") ? jsonObject.getString("title") : null;
        String description = jsonObject.has("description") ? jsonObject.getString("description") : null;
        String content = jsonObject.has("content") ? jsonObject.getString("content") : null;
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (user.getId().equals(userId) && post.getUser().getId().equals(user.getId())){
                postService.updatePost(post, title, description, content);
                return;
            }
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('post:delete', 'post:management')")
    public void deletePost(@RequestParam(name = "user_id") Long userId, @RequestParam Long id) throws NotFoundException {
        User user = AuthenticationUserHolder.getUser();
        Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            if (user.getId().equals(userId) && user.getId().equals(post.getUser().getId())){
                postTagsService.deleteAllByPost(post);
                postService.deleteById(id);
            }
        }
    }

    @PutMapping("/like")
    @PreAuthorize("hasAuthority('post:read')")
    public void putLike(@RequestParam Long id) throws NotFoundException {
        User user = AuthenticationUserHolder.getUser();
        Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            if (!post.getUser().getId().equals(user.getId())){
                postService.putLike(post);
            }
        }
    }

    @PutMapping("/view")
    @PreAuthorize("hasAuthority('post:read')")
    public void addView(@RequestParam Long id) throws NotFoundException {
        User user = AuthenticationUserHolder.getUser();
        Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            if (!post.getUser().getId().equals(user.getId())){
                postService.addView(post);
            }
        }
    }
}
