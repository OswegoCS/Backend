package com.csc380.codepeerreview.services;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PostService {

    @Resource
    private final PostDao postRepo;
    @Resource
    private final CommentDao commentRepo;
    private final ObjectMapper mapper;

    @Autowired
    public PostService(PostDao postRepo, CommentDao commentRepo, ObjectMapper mapper){
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.mapper = mapper;
    }

    public GetManyPostsResponse getAllPosts() {
        List<Post> posts = null;
        var response = new GetManyPostsResponse();
        posts = postRepo.findAll();
        posts.forEach(post -> post.setLikes(postRepo.getLikes(post.getId())));
        response.setPosts(posts);
        return response;
    }

    // Returns a Post with the given id
    public GetPostByIdResponse getPostsById(Integer id) {
        var response = new GetPostByIdResponse();
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
            var usersWhoLiked = commentRepo.getLikes(comment.getId());
            comment.setLikes(new Likes(usersWhoLiked));
        });
        response.setPost(post);
        response.setComments(comments);
        return response;
    }

    // Returns a list of all post ids
    public List<String> getIds() {
        return postRepo.getIds();
    }

    // Returns a list of all post ids
    public SearchPostsResponse searchPosts(String params) {
        var response = new SearchPostsResponse();
        String decodedParams = URLDecoder.decode(params, StandardCharsets.UTF_8)
            .replaceAll("\\p{Punct}", "");
        List<Post> posts = null;
        try {
            posts = postRepo.searchWithParams(decodedParams);
        } catch (Exception e) {
            // log the exception
        }

        response.setQuery(decodedParams);
        response.setPosts(posts);
        return response;
    }

    public ObjectNode createPost(CreatePostRequest request) {
        Post post = new Post(
            request.getScreenName(), request.getTitle(), request.getContent(), request.getCode());
        int id = postRepo.insertPost(post);
        ObjectNode response = mapper.createObjectNode();
        response.put("id", id);
        return response;
    }

    public ObjectNode editPost(EditPostRequest request) {
        Post post = new Post(
            request.getId(), request.getScreenName(), request.getTitle(), 
            request.getContent(),request.getCode());
        postRepo.updatePost(post);
        ObjectNode response = mapper.createObjectNode();
        response.put("id", request.getId());
        return response;
    }

    public void deletePost(Integer id) {
        postRepo.deletePost(id);
    }
}
