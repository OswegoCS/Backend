package com.csc380.codepeerreview.models;

import java.util.List;

public class Likes {

    private int quantity;
    private List<String> users;

    public Likes() {
        super();
    }

    public Likes(List<String> users) {
        this.users = users;
        this.quantity = users.size();
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public int getLikes() {
        return quantity;
    }

    public void setLikes(int likes) {
        this.quantity = likes;
    }

}
