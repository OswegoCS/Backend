package com.oswego.pcr.repositories.dao;

import java.util.List;

import com.oswego.pcr.models.User;

public interface UserDao {

    User findByEmail(String email);

    void insertUsers(List<User> users, String type);

    List<User> findByCourse(String course);

    User findCourseInstructor(String course);
}
