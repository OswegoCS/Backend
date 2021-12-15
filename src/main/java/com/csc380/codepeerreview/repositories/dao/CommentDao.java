package com.csc380.codepeerreview.repositories.dao;

import java.util.List;
import com.csc380.codepeerreview.models.Comment;
import com.csc380.codepeerreview.models.ReportedComment;

public interface CommentDao {

    List<Comment> findByPostId(Integer id);

    Comment insertComment(Comment comment);

    void deleteComment(Integer id);

    void reportComment(ReportedComment comment);

    void likeComment(Integer commentId, Integer userId);

    List<String> getLikes(Integer id);

    List<ReportedComment> getReportedComments();

}
