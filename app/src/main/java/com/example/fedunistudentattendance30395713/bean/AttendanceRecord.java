package com.example.fedunistudentattendance30395713.bean;

public class AttendanceRecord {
    private int recordID;
    private String date;
    private int studentID;
    private String attendanceStatus;

    private int courseID;

    public AttendanceRecord(int recordID, String date, int studentID, String attendanceStatus,int courseID) {
        this.recordID = recordID;
        this.date = date;
        this.studentID = studentID;
        this.attendanceStatus = attendanceStatus;
        this.courseID = courseID;
    }
// Getters and setters

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
}
