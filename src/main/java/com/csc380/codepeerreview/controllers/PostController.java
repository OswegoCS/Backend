package com.csc380.codepeerreview.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Instant;

import javax.annotation.Resource;

import com.csc380.codepeerreview.models.Comment;
import com.csc380.codepeerreview.models.Likes;
import com.csc380.codepeerreview.models.Post;
import com.csc380.codepeerreview.repositories.dao.CommentDao;
import com.csc380.codepeerreview.repositories.dao.PostDao;
import com.csc380.codepeerreview.requests.CreatePostRequest;
import com.csc380.codepeerreview.requests.EditPostRequest;
import com.csc380.codepeerreview.responses.CreatePostResponse;
import com.csc380.codepeerreview.responses.GetIdsResponse;
import com.csc380.codepeerreview.responses.GetManyPostsResponse;
import com.csc380.codepeerreview.responses.GetPostByIdResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PostController {
    @Resource
    public PostDao postRepo;
    @Resource
    public CommentDao commentRepo;

    @GetMapping(value = "/posts")
    public GetManyPostsResponse getAllPosts() {

        List<Post> posts = null;
        GetManyPostsResponse response = new GetManyPostsResponse();

        try {
            posts = postRepo.findAll();
            Collections.reverse(posts);
            response.setMessage("success");

        } catch (Exception e) {
            response.setMessage("failure caused by " + e.getMessage());
        }
        response.setPosts(posts);

        return response;
    }

    // Returns a Post with the given id
    @GetMapping(path = "/posts/id/{id}")
    public GetPostByIdResponse getPostsById(@PathVariable("id") Integer id) {
        GetPostByIdResponse response = new GetPostByIdResponse();
        List<Comment> comments = null;

        Post post = postRepo.findById(id);
        if (post == null) {
            response.setMessage("No posts with id " + id);
        }
        comments = commentRepo.findByPostId(id);

        for (Comment comment : comments) {
            List<String> usersWhoLiked = commentRepo.getLikes(comment.getId());
            comment.setLikes(new Likes(usersWhoLiked));
        }
        response.setPost(post);
        response.setComments(comments);
        return response;
    }

    // Returns a list of all post ids
    @GetMapping(path = "/posts/id")
    public GetIdsResponse getIds() {
        GetIdsResponse response = new GetIdsResponse();
        List<String> ids = postRepo.getIds();
        response.setIds(ids);
        return response;
    }

    @PostMapping("/posts/create")
    public CreatePostResponse createPost(@RequestBody CreatePostRequest request) {
        String title = request.getTitle();
        String content = request.getContent();
        String code = request.getCode();
        String screenName = request.getScreenName();

        if (code.equals("") || code == null) {
            code = "No code was provided for this post";
        }

        code = code.replaceAll("(\\\\r\\\\n|\\\\n)", "\n").replaceAll("(\\\\\")", "\"").replaceAll("(\\\\\\\\)",
                "\\\\");

        Post post = new Post();

        post.setTitle(title);
        post.setScreenName(screenName);
        post.setContent(content);
        post.setCode(code);

        int id = postRepo.insertPost(post);

        CreatePostResponse response = new CreatePostResponse();
        response.setId(id);

        return response;
    }

    @PutMapping("/posts/edit")
    public void editPost(@RequestBody EditPostRequest request) {
        int id = request.getId();
        String title = request.getTitle();
        String content = request.getContent();
        String code = request.getCode();
        String screenName = request.getScreenName();

        if (code.equals("") || code == null) {
            code = "No code was provided for this post";
        }

        code = code.replaceAll("(\\\\r\\\\n|\\\\n)", "\n").replaceAll("(\\\\\")", "\"").replaceAll("(\\\\\\\\)",
                "\\\\");

        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setScreenName(screenName);
        post.setContent(content);
        post.setCode(code);

        postRepo.updatePost(post);
    }

    @DeleteMapping("/posts/delete/{id}")
    public void deletePost(@PathVariable("id") Integer id) {
        postRepo.deletePost(id);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public ResourceNotFoundException() {

        }
    }
}
