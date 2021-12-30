package com.oswego.pcr.controllers;

import java.util.Map;

import com.oswego.pcr.models.Comment;
import com.oswego.pcr.models.ReportedComment;
import com.oswego.pcr.models.UserDetails;
import com.oswego.pcr.requests.CreateCommentRequest;
import com.oswego.pcr.requests.LikeCommentRequest;
import com.oswego.pcr.requests.ReportContentRequest;
import com.oswego.pcr.responses.GetReportedCommentsResponse;
import com.oswego.pcr.services.AuthService;
import com.oswego.pcr.services.CommentService;
import com.oswego.pcr.util.RoleHelper;

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
public class CommentsController {

    private final CommentService commentService;
    private final AuthService authService;

    @Autowired
    public CommentsController(CommentService commentService, AuthService authService) {
        this.commentService = commentService;
        this.authService = authService;
    }

    @PostMapping(value = "/comments")
    public Comment createComment(@RequestBody CreateCommentRequest request,
            @RequestHeader Map<String, String> headers) {
        var userDetails = authService.validateToken(headers.get("authorization"));
        if (!authService.isOwner(request.getScreenName(), userDetails))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        Comment comment = new Comment(
                request.getContent(), request.getPostId(), request.getScreenName());
        return commentService.createComment(comment);
    }

    @DeleteMapping(value = "/comments/{id}")
    public void deleteComment(@PathVariable Integer id, @RequestHeader Map<String, String> headers) {
        var userDetails = authService.validateToken(headers.get("authorization"));
        commentService.deleteComment(id, userDetails.getUser().getId());
    }

    @PutMapping(value = "/comments/report")
    public void reportComment(@RequestBody ReportContentRequest request, @RequestHeader Map<String, String> headers) {
        var userDetails = authService.validateToken(headers.get("authorization"));
        var reportedComment = new ReportedComment(request.getId(), userDetails.getUser().getId(), request.getReason(),
                "");
        commentService.reportComment(reportedComment);
    }

    @PostMapping(value = "/comments/like")
    public void likeComment(@RequestBody LikeCommentRequest request, @RequestHeader Map<String, String> headers) {
        authService.validateToken(headers.get("authorization"));
        commentService.likeComment(request.getId(), request.getUserId());
    }

    @GetMapping(value = "/comments/reported")
    public GetReportedCommentsResponse getReportedComments(@RequestHeader Map<String, String> headers) {
        var userDetails = authService.validateToken(headers.get("authorization"));
        canViewReported(userDetails);
        return commentService.getReportedComments();
    }

    @PutMapping(value = "/comments/{id}")
    public void editComment(@PathVariable String id, @RequestHeader Map<String, String> headers) {
        authService.validateToken(headers.get("authorization"));
    }

    private void canViewReported(UserDetails userDetails) {
        if (!RoleHelper.hasRole(userDetails.getUser().getRoles(), 1)
                || !RoleHelper.hasRole(userDetails.getUser().getRoles(), 2))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

}