package com.example.fedunistudentattendance30395713.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fedunistudentattendance30395713.bean.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private DbHelper mHelper;

    public StudentDao(Context context) {
        mHelper = new DbHelper(context);
    }

    public void insert(Student student) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String insert_sql = "INSERT INTO student (studentIDCard, name, courseID) VALUES (?,?,?)";
        Object[] obj = {student.getStudentIDCard(), student.getName(), student.getCourseID()};
        db.execSQL(insert_sql, obj);
        db.close();
    }

    public List<Student> query(int courseID) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        List<Student> students = new ArrayList<>();
        String query_sql = "SELECT * FROM student WHERE courseID = ?";
        Cursor cursor = db.rawQuery(query_sql, new String[]{String.valueOf(courseID)});

        if (cursor.getCount() == 0) {
            return students;
        }
        cursor.moveToFirst();
        do {
            int studentID = cursor.getInt(0);
            String studentIDCard = cursor.getString(1);
            String name = cursor.getString(2);
            int courseId = cursor.getInt(3);
            Student student = new Student(studentID, studentIDCard, name, courseId);
            students.add(student);
        } while (cursor.moveToNext());

        return students;
    }
    public String getStudentNameById(int studentId) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String query_sql = "SELECT name FROM student WHERE studentID = ?";
        Cursor cursor = db.rawQuery(query_sql, new String[]{String.valueOf(studentId)});

        String studentName = null;
        if (cursor.moveToFirst()) {
            studentName = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return studentName;
    }
}
