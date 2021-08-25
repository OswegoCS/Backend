package com.csc380.codepeerreview.responses;

import java.util.List;

public class GetCommentLikesResponse extends BasicResponse {

    private int commentId;
    private List<String> users;
    private int likes;

    public GetCommentLikesResponse() {
        super();
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

}
