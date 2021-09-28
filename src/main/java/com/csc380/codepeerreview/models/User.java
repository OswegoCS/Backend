package com.csc380.codepeerreview.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String screenName;
    private String type;
    private String course;
}