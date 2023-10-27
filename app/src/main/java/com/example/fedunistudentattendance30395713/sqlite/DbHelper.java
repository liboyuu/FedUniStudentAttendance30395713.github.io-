package com.example.fedunistudentattendance30395713.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DBname="datk.db";
    private static final int version=1;

    public DbHelper(Context context) {
        super(context, DBname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create user table
        String userTable = "create table user (id integer primary key autoincrement, username text(20), password text(20))";
        db.execSQL(userTable);

        // Create curriculum
        String courseTable = "create table course (courseID integer primary key autoincrement, courseName text(50), startDate text(20), endDate text(20), teacherID integer)";
        db.execSQL(courseTable);

        // Create student table
        String studentTable = "create table student (studentID integer primary key autoincrement, studentIDCard text(20), name text(50), courseID integer)";
        db.execSQL(studentTable);

        // Create attendance records
        String attendanceTable = "create table attendance (recordID integer primary key autoincrement, date text(20), studentID integer, attendanceStatus text(10),courseID integer)";
        db.execSQL(attendanceTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // When the database version changes, you can upgrade the database as required
    }
}

