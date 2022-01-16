package com.oswego.pcr.controllers;

import com.oswego.pcr.models.User;
import com.oswego.pcr.requests.CreateStudentsRequest;
import com.oswego.pcr.responses.GetProfileResponse;
import com.oswego.pcr.services.AuthService;
import com.oswego.pcr.services.UserService;
import com.oswego.pcr.util.FileHelper;
import com.oswego.pcr.util.RoleHelper;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    // Returns a profile of a user
    @GetMapping(path = "/users/profile")
    public GetProfileResponse getProfile(@RequestHeader Map<String, String> headers) {
        var userDetails = authService.validateToken(headers.get("authorization"));
        return userService.getProfile(userDetails.getEmail());
    }

    @PostMapping(path = "/users/create/instructors")
    public void createInstructors(@RequestBody CreateStudentsRequest request,
            @RequestHeader Map<String, String> headers) {
        var user = authService.validateToken(headers.get("authorization"));
        canUploadUsers(user);
        List<User> instructors = request.getUsers();
        userService.createInstructors(instructors);
    }

    @PostMapping(path = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadUsers(@RequestParam("file") MultipartFile studentCSV,
            @RequestHeader Map<String, String> headers) {
        var userDetails = authService.validateToken(headers.get("authorization"));
        canUploadUsers(userDetails);
        // Handle multipart file in request
        File tempFile = FileHelper.handleRequestMultipartCSVFile(studentCSV);
        // Send csv file to user service
        userService.createStudents(tempFile);
    }

    private void canUploadUsers(User user) {
        if (!RoleHelper.hasRole(user.getRoles(), 1)
                || !RoleHelper.hasRole(user.getRoles(), 2))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}