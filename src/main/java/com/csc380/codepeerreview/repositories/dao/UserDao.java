package com.csc380.codepeerreview.repositories.dao;

import java.util.List;

import com.csc380.codepeerreview.models.User;

public interface UserDao {

    User findById(Integer id);

    void insertUsers(List<User> users);

}
