package com.example.fedunistudentattendance30395713.bean;

public class Student {
    private int studentID;
    private String studentIDCard;
    private String name;
    private int courseID;

    public Student(int studentID, String studentIDCard, String name, int courseID) {
        this.studentID = studentID;
        this.studentIDCard = studentIDCard;
        this.name = name;
        this.courseID = courseID;
    }
// Getters and setters

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentIDCard() {
        return studentIDCard;
    }

    public void setStudentIDCard(String studentIDCard) {
        this.studentIDCard = studentIDCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}

