package com.csc380.codepeerreview.controllers;

import java.util.List;
import com.csc380.codepeerreview.models.User;
import com.csc380.codepeerreview.requests.CreateStudentsRequest;
import com.csc380.codepeerreview.responses.GetProfileResponse;
import com.csc380.codepeerreview.services.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    // Returns a profile of a user
    @GetMapping(path = "/users/profile/{email}")
    public GetProfileResponse getProfile(@PathVariable String email) {
        return userService.getProfile(email);
    }

    @GetMapping(path = "/users/validate/{email}")
    public ObjectNode validateUser(@PathVariable String email) {
        return userService.validateUser(email);
    }

    @PostMapping(path = "/users/create/students")
    public void createStudents(@RequestBody CreateStudentsRequest request) {
        List<User> students = request.getUsers();
        userService.createStudents(students);
    }

    @PostMapping(path = "/users/create/instructors")
    public void createInstructors(@RequestBody CreateStudentsRequest request) {
        List<User> instructors = request.getUsers();
        userService.createInstructors(instructors);
    }
}