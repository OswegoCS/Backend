package com.csc380.codepeerreview.services;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Resource;

import com.csc380.codepeerreview.models.Comment;
import com.csc380.codepeerreview.models.Post;
import com.csc380.codepeerreview.repositories.dao.CommentDao;
import com.csc380.codepeerreview.repositories.dao.PostDao;
import com.csc380.codepeerreview.responses.GetManyPostsResponse;
import com.csc380.codepeerreview.responses.GetPostByIdResponse;
import com.csc380.codepeerreview.responses.SearchPostsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        response.setPosts(posts);
        return response;
    }

    // Returns a Post with the given id
    public GetPostByIdResponse getPostsById(Integer id) {
        var response = new GetPostByIdResponse();
        List<Comment> comments = null;
        Post post = null;
        post = postRepo.findById(id);
        comments = commentRepo.findByPostId(id);
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
        posts = postRepo.searchWithParams(decodedParams);
        response.setQuery(decodedParams);
        response.setPosts(posts);
        return response;
    }

    public ObjectNode createPost(Post newPost) {
        int id = postRepo.insertPost(newPost);
        ObjectNode response = mapper.createObjectNode();
        response.put("id", id);
        return response;
    }

    public void editPost(Post postToEdit) {
        postRepo.updatePost(postToEdit);
    }

    public void deletePost(Integer id) {
        postRepo.deletePost(id);
    }
}
