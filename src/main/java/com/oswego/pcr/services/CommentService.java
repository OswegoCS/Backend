package com.oswego.pcr.services;

import java.util.List;

import javax.annotation.Resource;

import com.oswego.pcr.models.Comment;
import com.oswego.pcr.models.ReportedComment;
import com.oswego.pcr.repositories.dao.CommentDao;
import com.oswego.pcr.responses.GetReportedCommentsResponse;

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

    public Comment createComment(Comment newComment) {
        return commentRepo.insertComment(newComment);
    }

    public void deleteComment(Integer id, Integer userId) {
        commentRepo.deleteComment(id, userId);
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
