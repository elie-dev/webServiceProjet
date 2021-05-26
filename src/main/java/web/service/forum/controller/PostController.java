package web.service.forum.controller;

import java.io.IOException;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import web.service.forum.entity.Post;
import web.service.forum.entity.Topic;
import web.service.forum.repository.PostRepository;
import web.service.forum.security.service.UserDetailsServiceImpl;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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





   @PutMapping("/post")
   public ResponseEntity<Post> editPost(final @RequestBody Post post) {
       if ((UserDetailsServiceImpl.isAdmin()) || (UserDetailsServiceImpl.isModerator())) {
           return ResponseEntity.ok(postRepository.save(post));
       } else {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
       }
   }


}
