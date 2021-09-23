package com.csc380.codepeerreview.requests;

public abstract class BasicRequest {

    private int id;
    private String screenName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

}
