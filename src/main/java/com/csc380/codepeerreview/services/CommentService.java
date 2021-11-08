package com.csc380.codepeerreview.services;

import java.util.List;

import javax.annotation.Resource;

import com.csc380.codepeerreview.models.Comment;
import com.csc380.codepeerreview.models.ReportedComment;
import com.csc380.codepeerreview.repositories.dao.CommentDao;
import com.csc380.codepeerreview.responses.GetReportedCommentsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Resource
    private final CommentDao commentRepo;

    @Autowired
    public CommentService(CommentDao commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void createComment(Comment newComment) {
        commentRepo.insertComment(newComment);
    }

    public void deleteComment(Integer id) {
        commentRepo.deleteComment(id);
    }

    public void reportComment(ReportedComment comment) {
        commentRepo.reportComment(comment);
    }

    public void likeComment(Integer postId, Integer userId) {
        commentRepo.likeComment(postId, userId);
    }

    public GetReportedCommentsResponse getReportedComments() {
        var response = new GetReportedCommentsResponse();
        List<ReportedComment> comments = commentRepo.getReportedComments();
        response.setComments(comments);
        return response;

    }

    public void editComment(String id) {

    }
}
