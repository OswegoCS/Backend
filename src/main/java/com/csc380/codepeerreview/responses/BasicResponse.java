package com.csc380.codepeerreview.responses;

import java.io.Serializable;

public class BasicResponse implements Serializable {

    public int statusCode;
    public String message;

    public BasicResponse() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
