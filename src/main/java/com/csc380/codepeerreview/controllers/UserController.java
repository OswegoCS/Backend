package com.csc380.codepeerreview.controllers;

import com.csc380.codepeerreview.models.User;
import com.csc380.codepeerreview.requests.CreateStudentsRequest;
import com.csc380.codepeerreview.responses.GetProfileResponse;
import com.csc380.codepeerreview.services.UserService;
import com.csc380.codepeerreview.util.FileHelper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
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

    @PostMapping(path = "/users/create/instructors")
    public void createInstructors(@RequestBody CreateStudentsRequest request) {
        List<User> instructors = request.getUsers();
        userService.createInstructors(instructors);
    }

    @PostMapping(
        path = "/users",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadUsers(@RequestParam("file") MultipartFile studentCSV) {
        //Handle multipart file in request
        File tempFile = FileHelper.handleRequestMultipartCSVFile(studentCSV);
        //Send csv file to user service
        userService.createStudents(tempFile);
    }
}