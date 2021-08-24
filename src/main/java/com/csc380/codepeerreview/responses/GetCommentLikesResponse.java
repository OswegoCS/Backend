package com.csc380.codepeerreview.responses;

public class GetCommentLikesResponse extends BasicResponse {

    private int commentId;
    private int userId;

    public GetCommentLikesResponse() {
        super();
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
