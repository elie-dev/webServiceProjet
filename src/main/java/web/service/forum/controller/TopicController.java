package web.service.forum.controller;

import java.util.ArrayList;
import java.util.List;

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

import web.service.forum.entity.Categorie;
import web.service.forum.entity.Topic;
import web.service.forum.repository.TopicRepository;
import web.service.forum.security.service.UserDetailsServiceImpl;


@RestController
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;


    @ResponseBody
    @GetMapping("/topic")
    public Page<Topic> getTopic(Pageable pageable) {
        return topicRepository.findAll(pageable);
    }

    @PostMapping("/topic")
    public ResponseEntity<Topic> addTopic(@RequestBody Topic topic) {
        if (UserDetailsServiceImpl.isAdmin()) {
            return ResponseEntity.ok(topicRepository.save(topic));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @ResponseBody
    @GetMapping("/topic/{id}")
    public Topic getTopicById(final @PathVariable("id") String topicId) {
        try {
            return topicRepository.findById(Integer.valueOf(topicId)).get();
        } catch (Exception e) {
            return null;
        }
    }

}
