package com.oswego.pcr.services;

import java.io.File;
import java.util.List;
import javax.annotation.Resource;

import com.oswego.pcr.models.Post;
import com.oswego.pcr.models.User;
import com.oswego.pcr.repositories.dao.PostDao;
import com.oswego.pcr.repositories.dao.UserDao;
import com.oswego.pcr.responses.GetProfileResponse;
import com.oswego.pcr.util.FileHelper;
import com.oswego.pcr.util.RoleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    private final UserDao userRepo;
    @Resource
    private final PostDao postRepo;

    @Autowired
    public UserService(UserDao userRepo, PostDao postRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    // Returns a profile of a user
    public GetProfileResponse getProfile(String email) {
        GetProfileResponse response = new GetProfileResponse();
        String decodedEmail = email.replace("%40", "");
        User user = userRepo.findByEmail(decodedEmail);
        List<Post> posts = postRepo.findByUserId(user.getId());
        if (RoleHelper.hasRole(user.getRoles(), 2)) {
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

    public void createStudents(File studentCSV) {
        List<User> students = FileHelper.convertCSVtoUsers(studentCSV);
        userRepo.insertUsers(students, "students");
    }

    public void createInstructors(List<User> instructors) {
        userRepo.insertUsers(instructors, "instructor");
    }

}
