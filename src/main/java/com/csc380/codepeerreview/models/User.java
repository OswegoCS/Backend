package com.csc380.codepeerreview.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User {
    // associate users by username

    // Mongo ID
    @Id
    private ObjectId id;

    // User info
    private String nameLast;
    private String nameFirst;
    private String email;
    private int schoolID;
    private String username;
    private String type;
    private String course;
    //private String studentGrade;

    public User() {
        super();
    }

    // Constructor without the _id from Mongo makes it easier to parse students from
    // CSV
    public User(String nameLast, String nameFirst, String email, int schoolID, String type) {
        super();
        this.schoolID = schoolID;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
        this.email = email;
        this.type = type;
    }

    public String makeUsername(String email) {
        return email.substring(0, email.indexOf('@'));
    }

    public String getId() {
        return this.id.toHexString();
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getSchoolID() {
        return this.schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public String getNameFirst() {
        return this.nameFirst;
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    public String getNameLast() {
        return this.nameLast;
    }

    public void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse() {
        return this.course;
    }
}