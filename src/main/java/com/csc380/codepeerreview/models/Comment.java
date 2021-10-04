package com.csc380.codepeerreview.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Comment {

    private int id;
    private int postId;
    private int userId;
    private String screenName;
    private String content;
    private String date;
    private Likes likes;

    public Comment(String content, int postId, String screenName){
        this.content = content;
        this.postId = postId;
        this.screenName = screenName;
    }

}
