package com.csc380.codepeerreview.models;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Post {

    private int id;
    private String screenName;
    private String title;
    private String content;
    private String date;
    private String code;
    private String course;
    private List<LikeInfo> likes;

    public Post(int id, String screenName, String title, String content, String code) {
        this.id = id;
        this.screenName = screenName;
        this.title = title;
        this.content = content;
        this.code = code;
    }

    public Post(String screenName, String title, String content, String code) {
        this.screenName = screenName;
        this.title = title;
        this.content = content;
        this.code = code;
    }
}
