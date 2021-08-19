package com.csc380.codepeerreview.responses;

import java.util.List;

import com.csc380.codepeerreview.models.Post;

public class GetManyPostsResponse extends BasicResponse {

    public List<Post> posts;

    public GetManyPostsResponse(int statusCode, String message, List<Post> posts) {
        this.statusCode = statusCode;
        this.message = message;
        this.posts = posts;
    }

    public GetManyPostsResponse() {
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}
