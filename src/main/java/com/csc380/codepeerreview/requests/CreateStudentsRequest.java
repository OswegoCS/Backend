package com.csc380.codepeerreview.requests;

import java.util.List;
import com.csc380.codepeerreview.models.User;

public class CreateStudentsRequest extends BasicRequest {

    private List<User> students;

    public CreateStudentsRequest() {
        super();
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

}
