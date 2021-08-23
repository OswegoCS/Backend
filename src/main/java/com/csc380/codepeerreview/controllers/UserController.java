package com.csc380.codepeerreview.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.csc380.codepeerreview.repositories.dao.UserDao;
import com.csc380.codepeerreview.requests.CreateStudentsRequest;
import com.csc380.codepeerreview.repositories.dao.PostDao;
import com.csc380.codepeerreview.models.Post;
import com.csc380.codepeerreview.models.User;

import com.csc380.codepeerreview.responses.GetProfileResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    @Resource
    public UserDao userRepo;
    @Resource
    public PostDao postRepo;

    // Returns a profile of a user
    @GetMapping(path = "/users/id/{id}")
    public GetProfileResponse getIds(@PathVariable Integer id) {
        GetProfileResponse response = new GetProfileResponse();

        User user = userRepo.findById(id);
        List<Post> posts = postRepo.findByUserId(id);
        response.setUser(user);
        response.setPosts(posts);
        return response;
    }

    @PostMapping(path = "users/create")
    public void createStudents(@RequestBody CreateStudentsRequest request) {

        List<User> students = request.getStudents();

        userRepo.insertUsers(students);
    }
}