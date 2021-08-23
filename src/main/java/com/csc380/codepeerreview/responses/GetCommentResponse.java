package com.csc380.codepeerreview.responses;

import com.csc380.codepeerreview.models.Comment;

public class GetCommentResponse extends BasicResponse {

    private Comment comment;

    public GetCommentResponse() {
        super();
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

}
