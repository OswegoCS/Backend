package com.csc380.codepeerreview.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Courses")
public class Course {
    // wariables
    @Id
    private ObjectId _id;
    private String name;
    private String professorEmail;
    private User[] students;
    private ObjectId gradebookID;

    // Constructor for Liam's CSV code. Front end currently does not pass back a CRN or a professor email with the CSV
    public Course(String courseID, User[] students) {
        super();
        this.name = courseID;
        this.students = students;
    }

    public Course(String courseName, String professorEmail) {
        super();
        this.name = courseName;
        this.professorEmail = professorEmail;
    }

    public Course() {
        super();
    }

    public String getId() {
        // returns id number that we want
        return _id.toHexString();
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String courseName) {
        this.name = courseName;
    }

    public String getProfessorEmail() {
        return professorEmail;
    }

    public void setProfessorEmail(String email) {
        this.professorEmail = email;
    }

    public User[] getStudents() {
        return students;
    }

    public void setStudents(User[] students) {
        this.students = students;
    }

    public ObjectId getGradebookID() {
        return gradebookID;
    }

    public void setGradebookID(ObjectId gradebookID) {
        this.gradebookID = gradebookID;
    }

}
