package com.csc380.codepeerreview.repositories.dao;

import java.util.List;

import com.csc380.codepeerreview.models.User;

public interface UserRepository {

    public User findById(int id);

    public List<User> findByCourse(String course);

    public List<User> findByType(String type);

    public void deleteById(int id);

}
