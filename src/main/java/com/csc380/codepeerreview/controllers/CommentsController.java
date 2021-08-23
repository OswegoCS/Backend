package com.csc380.codepeerreview.controllers;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import com.csc380.codepeerreview.models.Comment;
import com.csc380.codepeerreview.repositories.dao.CommentDao;
import com.csc380.codepeerreview.requests.CreateCommentRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CommentsController {

    @Resource
    public CommentDao commentRepo;

    @GetMapping(value = "/comments/create")
    public void createComment(CreateCommentRequest request) {

        Comment comment = new Comment();
        comment.setContent(request.getComment());
        comment.setPostId(request.getPostId());
        comment.setScreenName(request.getScreenName());
        comment.setDate(LocalDateTime.now().toString().substring(0, 10));

    }

}