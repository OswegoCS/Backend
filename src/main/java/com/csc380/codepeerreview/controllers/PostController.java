package com.csc380.codepeerreview.controllers;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.csc380.codepeerreview.models.Comment;
import com.csc380.codepeerreview.models.Likes;
import com.csc380.codepeerreview.models.Post;
import com.csc380.codepeerreview.repositories.dao.CommentDao;
import com.csc380.codepeerreview.repositories.dao.PostDao;
import com.csc380.codepeerreview.requests.CreatePostRequest;
import com.csc380.codepeerreview.requests.EditPostRequest;
import com.csc380.codepeerreview.responses.GetManyPostsResponse;
import com.csc380.codepeerreview.responses.GetPostByIdResponse;
import com.csc380.codepeerreview.responses.SearchPostsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PostController {
    @Resource
    public PostDao postRepo;
    @Resource
    public CommentDao commentRepo;

    @Autowired
    public ObjectMapper mapper;

    @GetMapping(value = "/posts")
    public GetManyPostsResponse getAllPosts() {
        List<Post> posts = null;
        GetManyPostsResponse response = new GetManyPostsResponse();
        posts = postRepo.findAll();
        posts.forEach(post -> post.setLikes(postRepo.getLikes(post.getId())));
        response.setPosts(posts);
        return response;
    }

    // Returns a Post with the given id
    @GetMapping(path = "/posts/id/{id}")
    public GetPostByIdResponse getPostsById(@PathVariable("id") Integer id) {
        GetPostByIdResponse response = new GetPostByIdResponse();
        List<Comment> comments = null;
        Post post = null;
        try {
            post = postRepo.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No posts with id: " + id);
        }
        post.setLikes(postRepo.getLikes(post.getId()));
        comments = commentRepo.findByPostId(id);

        comments.forEach(comment -> {
            List<String> usersWhoLiked = commentRepo.getLikes(comment.getId());
            comment.setLikes(new Likes(usersWhoLiked));
        });

        response.setPost(post);
        response.setComments(comments);
        return response;
    }

    // Returns a list of all post ids
    @GetMapping(path = "/posts/id")
    public List<String> getIds() {
        List<String> ids = postRepo.getIds();
        return ids;
    }

    // Returns a list of all post ids
    @GetMapping(path = "/posts/search/{params}")
    public SearchPostsResponse searchPosts(@PathVariable String params) {
        SearchPostsResponse response = new SearchPostsResponse();
        String decodedParams = URLDecoder.decode(params, StandardCharsets.UTF_8)
            .replaceAll("\\p{Punct}", "");
        List<Post> posts = new ArrayList<Post>();
        try {
            posts = postRepo.searchWithParams(decodedParams);
        } catch (Exception e) {
            // log the exception
        }

        response.setQuery(decodedParams);
        response.setPosts(posts);
        return response;
    }

    @PostMapping("/posts/create")
    public ObjectNode createPost(@RequestBody CreatePostRequest request) {
        Post post = new Post(
            request.getScreenName(), request.getTitle(), request.getContent(), request.getCode());
        int id = postRepo.insertPost(post);
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("id", id);
        return objectNode;
    }

    @PutMapping("/posts/edit")
    public void editPost(@RequestBody EditPostRequest request) {
        Post post = new Post(
            request.getId(), request.getScreenName(), request.getTitle(), request.getContent(),request.getCode());
        postRepo.updatePost(post);

    }

    @DeleteMapping("/posts/delete/id/{id}")
    public void deletePost(@PathVariable("id") Integer id) {
        postRepo.deletePost(id);
    }
}
