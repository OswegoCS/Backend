package com.csc380.codepeerreview.models;

public class Comment {

    private int id;
    private int postId;
    private int userId;
    private String screenName;
    private String content;
    private String date;
    private Likes likes;

    public Comment() {
        super();
    }

    public Comment(String content, String date) {
        this.content = content;
        this.date = date;
    }

    public Comment(int id, String content, String date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public Comment(int id, String screenName, String content, String date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

}
