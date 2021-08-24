package com.csc380.codepeerreview.controllers;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import com.csc380.codepeerreview.models.Comment;
import com.csc380.codepeerreview.repositories.dao.CommentDao;
import com.csc380.codepeerreview.requests.CreateCommentRequest;
import com.csc380.codepeerreview.requests.LikeCommentRequest;
import com.csc380.codepeerreview.requests.ReportContentRequest;
import com.csc380.codepeerreview.responses.GetCommentLikesResponse;

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
public class CommentsController {

    @Resource
    public CommentDao commentRepo;

    @PostMapping(value = "/comments/create")
    public void createComment(@RequestBody CreateCommentRequest request) {

        Comment comment = new Comment();
        comment.setContent(request.getComment());
        comment.setPostId(request.getPostId());
        comment.setScreenName(request.getScreenName());
        comment.setDate(LocalDateTime.now().toString().substring(0, 10));
        commentRepo.insertComment(comment);

    }

    @DeleteMapping(value = "/comments/id/{id}")
    public void findById(@PathVariable Integer id) {
        commentRepo.deleteComment(id);

    }

    @PutMapping(value = "comments/report")
    public void reportComment(@RequestBody ReportContentRequest request) {
        commentRepo.reportComment(request.getId(), request.getReporterId(), request.getReason());
    }

    @PostMapping(value = "comments/like")
    public void likeComment(@RequestBody LikeCommentRequest request) {
        commentRepo.likeComment(request.getId(), request.getUserId());
    }

    @GetMapping(value = "comments/likes/{id}")
    public GetCommentLikesResponse getLikes(@PathVariable Integer id) {

        GetCommentLikesResponse response;
        response = commentRepo.getLikes(id);
        return response;

    }

}