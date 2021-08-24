package com.csc380.codepeerreview.requests;

public class LikeCommentRequest extends BasicRequest {

    private int userId;

    public LikeCommentRequest() {
        super();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
