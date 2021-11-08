package com.csc380.codepeerreview.controllers;

import com.csc380.codepeerreview.models.Comment;
import com.csc380.codepeerreview.models.ReportedComment;
import com.csc380.codepeerreview.repositories.dao.CommentDao;
import com.csc380.codepeerreview.requests.CreateCommentRequest;
import com.csc380.codepeerreview.requests.LikeCommentRequest;
import com.csc380.codepeerreview.requests.ReportContentRequest;
import com.csc380.codepeerreview.responses.GetReportedCommentsResponse;
import com.csc380.codepeerreview.services.CommentService;

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
public class CommentsController {

    private final CommentService commentService;

    @Autowired
    public CommentsController(CommentService commentService,CommentDao commentRepo) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/comments/create")
    public void createComment(@RequestBody CreateCommentRequest request) {
        Comment comment = new Comment(
            request.getComment(), request.getPostId(), request.getScreenName());
        commentService.createComment(comment);
    }

    @DeleteMapping(value = "/comments/id/{id}")
    public void deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
    }

    @PutMapping(value = "/comments/report")
    public void reportComment(@RequestBody ReportContentRequest request) {
        var reportedComment = new ReportedComment(request.getId(), request.getReporterId(), request.getReason(), "");
        commentService.reportComment(reportedComment);
    }

    @PostMapping(value = "/comments/like")
    public void likeComment(@RequestBody LikeCommentRequest request) {
        commentService.likeComment(request.getId(), request.getUserId());
    }

    @GetMapping(value = "/comments/reported")
    public GetReportedCommentsResponse getReportedComments() {
        return commentService.getReportedComments();
    }

    @PutMapping(value = "/comments/edit/{id}")
    public void editComment(@PathVariable String id) {

    }

}