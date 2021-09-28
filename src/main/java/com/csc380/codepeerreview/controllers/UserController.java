package com.csc380.codepeerreview.controllers;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Resource;

import com.csc380.codepeerreview.models.Post;
import com.csc380.codepeerreview.models.User;
import com.csc380.codepeerreview.repositories.dao.PostDao;
import com.csc380.codepeerreview.repositories.dao.UserDao;
import com.csc380.codepeerreview.requests.CreateStudentsRequest;
import com.csc380.codepeerreview.responses.GetProfileResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Autowired
    public ObjectMapper mapper;

    // Returns a profile of a user
    @GetMapping(path = "/users/profile/{email}")
    public GetProfileResponse getIds(@PathVariable String email) {
        GetProfileResponse response = new GetProfileResponse();
        String decodedEmail = email.replace("%40", "");
        User user = userRepo.findByEmail(decodedEmail);
        List<Post> posts = postRepo.findByUserId(user.getId());
        response.setUser(user);
        response.setPosts(posts);
        return response;
    }

    @GetMapping(path = "/users/validate/{email}")
    public ObjectNode validateUser(@PathVariable String email) {
        String decodedEmail = URLDecoder.decode(email, StandardCharsets.UTF_8);
        boolean validity = userRepo.findByEmail(decodedEmail) != null ? true : false;
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("validity", validity);
        return objectNode;
    }

    @PostMapping(path = "/users/create/students")
    public void createStudents(@RequestBody CreateStudentsRequest request) {

        List<User> students = request.getUsers();

        userRepo.insertUsers(students, "students");
    }

    @PostMapping(path = "/users/create/instructors")
    public void createInstructors(@RequestBody CreateStudentsRequest request) {

        List<User> instructors = request.getUsers();

        userRepo.insertUsers(instructors, "instructor");
    }
}