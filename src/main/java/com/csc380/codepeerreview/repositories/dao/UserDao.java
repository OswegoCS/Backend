package com.csc380.codepeerreview.repositories.dao;

import java.util.List;

import com.csc380.codepeerreview.models.User;

public interface UserDao {

    User findById(Integer id);

    User findByEmail(String email);

    void insertUsers(List<User> users, String type);

    List<String> getUserIds();

}
