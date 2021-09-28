package com.csc380.codepeerreview.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @NoArgsConstructor
public class ReportedComment {

    private String author;
    private String reporter;
    private int commentId;
    private int authorId;
    private int reporterId;
    private int postId;
    private String reason;
    private String content;
    private String date;

}
