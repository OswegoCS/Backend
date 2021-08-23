package com.csc380.codepeerreview.responses;

import java.util.List;

import com.csc380.codepeerreview.models.Post;
import com.csc380.codepeerreview.models.User;

public class GetProfileResponse extends BasicResponse {

    private User user;
    private List<Post> posts;

    public GetProfileResponse() {
        super();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}
