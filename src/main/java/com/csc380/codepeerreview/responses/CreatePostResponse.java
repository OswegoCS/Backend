package com.csc380.codepeerreview.responses;

public class CreatePostResponse extends BasicResponse {

    public int id;

    public CreatePostResponse() {
        super();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
