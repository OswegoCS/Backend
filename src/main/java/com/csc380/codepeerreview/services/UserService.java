package com.csc380.codepeerreview.services;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.annotation.Resource;
import com.csc380.codepeerreview.models.Post;
import com.csc380.codepeerreview.models.Role;
import com.csc380.codepeerreview.models.User;
import com.csc380.codepeerreview.repositories.dao.PostDao;
import com.csc380.codepeerreview.repositories.dao.UserDao;
import com.csc380.codepeerreview.responses.GetProfileResponse;
import com.csc380.codepeerreview.util.FileHelper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    private final UserDao userRepo;
    @Resource
    private final PostDao postRepo;
    private final ObjectMapper mapper;

    @Autowired
    public UserService(UserDao userRepo, PostDao postRepo, ObjectMapper mapper) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.mapper = mapper;
    }
    // Returns a profile of a user
    public GetProfileResponse getProfile(String email) {
        GetProfileResponse response = new GetProfileResponse();
        String decodedEmail = email.replace("%40", "");
        User user = userRepo.findByEmail(decodedEmail);
        List<Post> posts = postRepo.findByUserId(user.getId());
        if(hasRole(user.getRoles(), 2)) {
            List<User> students = userRepo.findByCourse(user.getCourse());
            response.setStudents(students);
        } else {
            User instructor = userRepo.findCourseInstructor(user.getCourse());
            response.setInstructor(instructor);
        }
        response.setUser(user);
        response.setPosts(posts);
        return response;
    }

    public ObjectNode validateUser(String email) {
        String decodedEmail = URLDecoder.decode(email, StandardCharsets.UTF_8);
        boolean validity = userRepo.findByEmail(decodedEmail) != null ? true : false;
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("validity", validity);
        return objectNode;
    }

    public void createStudents(File studentCSV) {
        List<User> students = FileHelper.convertCSVtoUsers(studentCSV);
        userRepo.insertUsers(students, "students");
    }

    public void createInstructors(List<User> instructors) {
        userRepo.insertUsers(instructors, "instructor");
    }

    private boolean hasRole(List<Role> roles, int roleId) {
        for(var role: roles) {
            if(role.getRoleId() == roleId) return true;
        }
        return false;
    }

}
