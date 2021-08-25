package com.csc380.codepeerreview.requests;

import java.util.List;
import com.csc380.codepeerreview.models.User;

public class CreateStudentsRequest extends BasicRequest {

    private List<User> users;

    public CreateStudentsRequest() {
        super();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
