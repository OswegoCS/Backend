package com.csc380.codepeerreview.repositories.dao;

import java.util.List;
import com.csc380.codepeerreview.models.Comment;

public interface CommentDao {

    List<Comment> findByPostId(Integer id);

    List<Comment> findByReported();

    void insertComment(Comment comment);

    void deleteComment(Integer id);

}
