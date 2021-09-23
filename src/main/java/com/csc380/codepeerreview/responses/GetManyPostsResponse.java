package com.csc380.codepeerreview.responses;

import java.util.List;

import com.csc380.codepeerreview.models.Post;

public class GetManyPostsResponse extends BasicResponse {

    public List<Post> posts;

    public GetManyPostsResponse() {
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}
