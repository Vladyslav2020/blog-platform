package com.example.blogplatform.controller;

import com.example.blogplatform.model.Comment;
import com.example.blogplatform.model.Post;
import com.example.blogplatform.model.User;
import com.example.blogplatform.model.dto.CommentDTO;
import com.example.blogplatform.security.AuthenticationUserHolder;
import com.example.blogplatform.security.Role;
import com.example.blogplatform.service.CommentService;
import com.example.blogplatform.service.PostService;
import com.example.blogplatform.service.UserService;
import javassist.NotFoundException;
import org.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/of-post/{id}")
    public List<CommentDTO> getCommentsOfPost(@PathVariable(name = "id") Long postId) throws NotFoundException {
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isPresent()){
            return commentService.getCommentsOfPost(optionalPost.get()).stream().map(
                    item -> new CommentDTO(item.getId(),
                            item.getUser().getId(),
                            item.getPost().getId(),
                            item.getContent(),
                            item.getLikes(),
                            item.getReplyBy() != null? item.getReplyBy().getId(): null,
                            item.getPublicationDate().toString())
            ).collect(Collectors.toList());
        }
        return null;
    }

    @GetMapping("/of-user/{id}")
    public List<CommentDTO> getCommentsOfUser(@PathVariable(name = "id") Long userId){
        User user = AuthenticationUserHolder.getUser();
        if (!user.getId().equals(userId) && !user.getRole().equals(Role.ADMIN))
            return null;
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isPresent()){
            return commentService.getCommentsOfUser(optionalUser.get()).stream().map(
                    item -> new CommentDTO(item.getId(),
                            item.getUser().getId(),
                            item.getPost().getId(),
                            item.getContent(), item.getLikes(),
                            item.getReplyBy() != null? item.getReplyBy().getId(): null,
                            item.getPublicationDate().toString())
            ).collect(Collectors.toList());
        }
        return null;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('comment:create')")
    public void addComment(@RequestParam(name = "post_id") Long postId, @RequestParam(name = "user_id") Long userId, @RequestBody String body) throws NotFoundException {
        /*
            - content
            - ?replyBy
         */
        User user = AuthenticationUserHolder.getUser();
        if (user.getId().equals(userId)){
            Optional<Post> optionalPost = postService.findById(postId);
            if (optionalPost.isPresent() && optionalPost.get().getUser().getId().equals(user.getId())){
                JSONObject jsonObject = new JSONObject(body);
                String content = jsonObject.getString("content");
                Long replyById = jsonObject.has("replyBy")? jsonObject.getLong("replyBy"): null;
                Optional<Comment> optionalComment = replyById != null? commentService.findById(replyById): Optional.empty();
                Comment replyBy = null;
                if (optionalComment.isPresent()){
                    replyBy = optionalComment.get();
                }
                commentService.add(user, optionalPost.get(), content, replyBy);
            }
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('comment:update')")
    public void updateComment(@PathVariable Long id, @RequestBody String body){
        /*
            - ?content
         */
        User user = AuthenticationUserHolder.getUser();
        Optional<Comment> optionalComment = commentService.findById(id);
        if (optionalComment.isPresent() && optionalComment.get().getUser().getId().equals(user.getId())){
            JSONObject jsonObject = new JSONObject(body);
            String content = jsonObject.has("content")? jsonObject.getString("content"): null;
            commentService.updateComment(optionalComment.get(), content);
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('comment:delete', 'comment:management')")
    public void deleteComment(@PathVariable Long id){
        User user = AuthenticationUserHolder.getUser();
        Optional<Comment> optionalComment = commentService.findById(id);
        if (optionalComment.isPresent() && optionalComment.get().getUser().getId().equals(user.getId())){
            commentService.deleteComment(id);
        }
    }

    @PutMapping("/like")
    @PreAuthorize("hasAuthority('comment:read')")
    public void putLike(@RequestParam(name = "comment_id") Long commentId){
        User user = AuthenticationUserHolder.getUser();
        Optional<Comment> optionalComment = commentService.findById(commentId);
        if (optionalComment.isPresent() && !optionalComment.get().getUser().getId().equals(user.getId())){
            commentService.putLike(optionalComment.get());
        }
    }
}
