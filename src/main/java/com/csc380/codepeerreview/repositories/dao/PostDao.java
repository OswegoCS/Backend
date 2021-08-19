package com.csc380.codepeerreview.repositories.dao;

import java.util.List;
import com.csc380.codepeerreview.models.Post;

public interface PostDao {

    List<Post> findAll();

    // List<Post> findByCourse(String course);

    // List<Post> findReportedPosts();

    Post findById(Integer id);

    // void deleteById(Integer id);

    // void updatePost(Post post);

    int insertPost(Post post);

    List<String> getIds();

    // void executeUpdateEmployee(Post post);
}
