package com.oswego.pcr.services;

import java.util.List;

import javax.annotation.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.oswego.pcr.models.Comment;
import com.oswego.pcr.models.Post;
import com.oswego.pcr.repositories.dao.CommentDao;
import com.oswego.pcr.repositories.dao.PostDao;
import com.oswego.pcr.responses.GetManyPostsResponse;
import com.oswego.pcr.responses.GetPostByIdResponse;

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
    public PostService(PostDao postRepo, CommentDao commentRepo, ObjectMapper mapper) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.mapper = mapper;

    }

    public GetManyPostsResponse getAllPosts() {
        var response = new GetManyPostsResponse();
        var posts = postRepo.findAll();
        response.setPosts(posts);
        return response;
    }

    // Returns a Post with the given id
    public GetPostByIdResponse getPostsById(Integer id) {
        var response = new GetPostByIdResponse();
        Post post = postRepo.findById(id);
        List<Comment> comments = commentRepo.findByPostId(id);
        response.setPost(post);
        response.setComments(comments);
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

    public void deletePost(Integer id, Integer userId) {
        postRepo.deletePost(id, userId);
    }
}
