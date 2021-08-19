package com.csc380.codepeerreview.requests;

public class CreatePostRequest extends BasicRequest {

    private String title;
    private String content;
    private String course;
    private String date;
    private String code;
    private String file;

    public CreatePostRequest() {
        super();
    }

    public CreatePostRequest(int id, String screenName, String title, String content, String date, String code) {
        this.id = id;
        this.screenName = screenName;
        this.title = title;
        this.content = content;
        this.date = date;
        this.code = code;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
