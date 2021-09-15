package com.csc380.codepeerreview.models;

import java.util.List;

public class Likes {

    private int count;
    private List<String> users;

    public Likes() {
        super();
    }

    public Likes(List<String> users) {
        this.users = users;
        this.count = users.size();
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
