package com.csc380.codepeerreview.models;

public class Post {

    private int id;
    private String screenName;
    private String title;
    private String content;
    private String date;
    private String code;
    private String course;

    public Post() {
        super();
    }

    public Post(int id, String title, String content, String date, String code) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.code = code;
    }

    public Post(String title, String content, String date, String code) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.code = code;
    }

    public Post(String screenName, String title, String course, String content, String date, String code) {
        this.screenName = screenName;
        this.title = title;
        this.content = content;
        this.date = date;
        this.code = code;
    }

    public Post(int id, String screenName, String title, String course, String content, String date, String code) {
        this.id = id;
        this.screenName = screenName;
        this.title = title;
        this.content = content;
        this.date = date;
        this.code = code;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

}
