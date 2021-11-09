package com.csc380.codepeerreview.controllers;

import com.csc380.codepeerreview.requests.CreatePostRequest;
import com.csc380.codepeerreview.requests.EditPostRequest;
import com.csc380.codepeerreview.responses.GetManyPostsResponse;
import com.csc380.codepeerreview.responses.GetPostByIdResponse;
import com.csc380.codepeerreview.responses.SearchPostsResponse;
import com.csc380.codepeerreview.services.PostService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @Autowired
    public PostController(PostService postService, ObjectMapper mapper){
        this.postService = postService;
    }

    @GetMapping(value = "/posts")
    public GetManyPostsResponse getAllPosts() {
        logger.info("Attempting to retrieve all posts");
        return postService.getAllPosts();
    }

    // Returns a Post with the given id
    @GetMapping(path = "/posts/id/{id}")
    public GetPostByIdResponse getPostsById(@PathVariable("id") Integer id) {
        return postService.getPostsById(id);
    }

    // Returns a list of all post ids
    @GetMapping(path = "/posts/id")
    public List<String> getIds() {
        return postService.getIds();
    }

    // Returns a list of all post ids
    @GetMapping(path = "/posts/search/{params}")
    public SearchPostsResponse searchPosts(@PathVariable String params) {
        return postService.searchPosts(params);
    }

    @PostMapping("/posts/create")
    public ObjectNode createPost(@RequestBody CreatePostRequest request) {
        return postService.createPost(request);
    }

    @PutMapping("/posts/edit")
    public ObjectNode editPost(@RequestBody EditPostRequest request) {
        return postService.editPost(request);
    }

    @DeleteMapping("/posts/delete/id/{id}")
    public void deletePost(@PathVariable("id") Integer id) {
        postService.deletePost(id);
    }
}
