package com.csc380.codepeerreview.responses;

public class BasicResponse {

    public int statusCode;
    public String message;

    public BasicResponse() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
