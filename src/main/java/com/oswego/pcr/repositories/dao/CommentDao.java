package com.oswego.pcr.repositories.dao;

import java.util.List;

import com.oswego.pcr.models.Comment;
import com.oswego.pcr.models.ReportedComment;

public interface CommentDao {

    List<Comment> findByPostId(Integer id);

    Comment insertComment(Comment comment);

    void deleteComment(Integer id, Integer userId);

    void reportComment(ReportedComment comment);

    void likeComment(Integer commentId, Integer userId);

    List<String> getLikes(Integer id);

    List<ReportedComment> getReportedComments();

}
