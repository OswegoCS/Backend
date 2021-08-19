package com.csc380.codepeerreview.requests;

public class CreateCommentRequest extends BasicRequest {

    private String comment;
    private int postId;

    public CreateCommentRequest() {
        super();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPostId() {
        return postId;
    }

    public void setId(int postId) {
        this.postId = postId;
    }
}
