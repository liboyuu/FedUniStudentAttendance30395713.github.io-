package com.example.fedunistudentattendance30395713.bean;

public class Course {
    private int courseID;
    private String courseName;
    private String startDate;
    private String endDate;
    private int teacherID;

    public Course(int courseID, String courseName, String startDate, String endDate, int teacherID) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacherID = teacherID;
    }
// Getters and setters

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }
}
