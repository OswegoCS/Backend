package com.oswego.pcr.repositories.dao;

import java.util.List;

import com.oswego.pcr.models.Post;

public interface PostDao {

    List<Post> findAll();

    Post findById(Integer id);

    List<Post> findByUserId(Integer id);

    void updatePost(Post post);

    int insertPost(Post post);

    void deletePost(Integer id, Integer userId);

}
