package com.example.blogplatform.service;

import com.example.blogplatform.model.Post;
import com.example.blogplatform.model.User;
import com.example.blogplatform.repository.PostRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Optional<Post> findById(Long id) throws NotFoundException {
        return postRepository.findById(id);
    }

    public List<Post> getAllUserPosts(User user){
        return postRepository.getAllByUser(user);
    }

    public Post addPost(User user, String title, String description, String content, Long likes, Long views){
        Post post = new Post(title, description, content, null, likes, views, LocalDateTime.now(), user);
        postRepository.save(post);
        return post;
    }

    @Transactional
    public Post updatePost(Post post, String title, String description, String content){
        if (title != null && !title.equals(post.getTitle())){
            post.setTitle(title);
        }
        if (description != null && !description.equals(post.getDescription())){
            post.setDescription(description);
        }
        if (content != null && !content.equals(post.getContent())){
            post.setContent(content);
        }
        return post;
    }

    @Transactional
    public Post putLike(Post post){
        post.setLikes(post.getLikes() + 1);
        return post;
    }

    @Transactional
    public Post addView(Post post){
        post.setViews(post.getViews() + 1);
        return post;
    }

    public void deleteById(Long id){
        if (postRepository.existsById(id))
            postRepository.deleteById(id);
    }
}
