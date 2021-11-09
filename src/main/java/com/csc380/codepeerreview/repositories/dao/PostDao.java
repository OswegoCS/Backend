package com.csc380.codepeerreview.repositories.dao;

import java.util.List;
import com.csc380.codepeerreview.models.Post;

public interface PostDao {

    List<Post> findAll();

    Post findById(Integer id);

    List<Post> findByUserId(Integer id);

    void updatePost(Post post);

    int insertPost(Post post);

    void deletePost(Integer id);

    List<String> getIds();

    List<Post> searchWithParams(String params);

}
