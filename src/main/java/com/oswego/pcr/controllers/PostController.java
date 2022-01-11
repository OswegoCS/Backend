package com.oswego.pcr.controllers;

import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.oswego.pcr.models.Post;
import com.oswego.pcr.requests.CreatePostRequest;
import com.oswego.pcr.requests.EditPostRequest;
import com.oswego.pcr.responses.GetManyPostsResponse;
import com.oswego.pcr.responses.GetPostByIdResponse;
import com.oswego.pcr.services.AuthService;
import com.oswego.pcr.services.PostService;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;
    private final AuthService authService;

    @Autowired
    public PostController(PostService postService, AuthService authService) {
        this.postService = postService;
        this.authService = authService;
    }

    @GetMapping(value = "/posts")
    public GetManyPostsResponse getAllPosts(@RequestHeader Map<String, String> headers) {
        authService.validateToken(headers.get("authorization"));
        logger.info("Attempting to get all posts");
        return postService.getAllPosts();
    }

    // Returns a Post with the given id
    @GetMapping(path = "/posts/{id}")
    public GetPostByIdResponse getPostsById(@PathVariable("id") Integer id,
            @RequestHeader Map<String, String> headers) {
        var user = authService.validateToken(headers.get("authorization"));
        var response = postService.getPostsById(id);
        response.setOwner(response.getPost().getUserId() == user.getId());
        return response;
    }

    @PostMapping("/posts")
    public ObjectNode createPost(@RequestBody CreatePostRequest request, @RequestHeader Map<String, String> headers) {
        var userDetails = authService.validateToken(headers.get("authorization"));
        if (!authService.isOwner(request.getScreenName(), userDetails))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        Post post = new Post(
                request.getScreenName(), request.getTitle(), request.getContent(), request.getCode());
        return postService.createPost(post);
    }

    @PutMapping("/posts/{id}")
    public void editPost(@RequestBody EditPostRequest request, @RequestHeader Map<String, String> headers) {
        var user = authService.validateToken(headers.get("authorization"));
        if (!authService.isOwner(request.getScreenName(), user))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        Post post = new Post(
                request.getId(), request.getScreenName(), request.getTitle(),
                request.getContent(), request.getCode());
        postService.editPost(post);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@RequestHeader Map<String, String> headers, @PathVariable("id") Integer id) {
        var user = authService.validateToken(headers.get("authorization"));
        // Only the owner of a post, admin, or instructor can delete
        postService.deletePost(id, user.getId());
    }
}
