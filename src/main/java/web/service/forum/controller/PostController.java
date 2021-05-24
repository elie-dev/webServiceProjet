package web.service.forum.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import web.service.forum.entity.Post;
import web.service.forum.repository.PostRepository;

/**
 *
 * @author Matthieu BACHELIER
 * @since 2021
 * @version 1.0
 */
@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;


    @ResponseBody
    @GetMapping("/post")
    public Page<Post> getPost(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @DeleteMapping("/post/{id}")
    public void deletePost(final @PathVariable("id") Integer postId) {
        postRepository.deleteById(postId);
    }

    @PostMapping("/post")
    public Post addPost(@RequestBody Post post) {
        Post postToSave = new Post();
        postToSave.setContent(post.getContent());
        postToSave.setCreatedAt(post.getCreatedAt());
        postToSave.setUpdatedAt(post.getUpdatedAt());
        postToSave = postRepository.save(postToSave);
        return postToSave;
    }

    @ResponseBody
    @GetMapping("/post/{id}")
    public Post getPostById(final @PathVariable("id") String categorieId) {
        try {
            return postRepository.findById(Integer.valueOf(categorieId)).get();
        } catch (Exception e) {
            return null;
        }
    }

    @ResponseBody
    @PutMapping("/post/{id}")
    public Post editpost(final @RequestBody Post post) {
        return postRepository.save(post);
    }


}
