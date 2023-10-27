package com.example.fedunistudentattendance30395713.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fedunistudentattendance30395713.bean.Course;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDao {
    private DbHelper mHelper;

    public CourseDao(Context context) {
        mHelper = new DbHelper(context);
    }

    public void insertCourse(String courseName, String startDate, int teacherID) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        // Calculate the end date (assuming 12 weeks is 84 days)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date start = sdf.parse(startDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            calendar.add(Calendar.DAY_OF_YEAR, 84); // 12 weeks
            Date end = calendar.getTime();
            String endDate = sdf.format(end);

            ContentValues values = new ContentValues();
            values.put("courseName", courseName);
            values.put("startDate", startDate);
            values.put("endDate", endDate);
            values.put("teacherID", teacherID);

            db.insert("course", null, values);

            db.close();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void deleteCourseById(int courseId) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.delete("course", "courseID=?", new String[]{String.valueOf(courseId)});

        db.close();
    }

    public List<Course> getCoursesByTeacherID(int teacherID) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        List<Course> courses = new ArrayList<>();
        String query_sql = "SELECT * FROM course WHERE teacherID = ?";
        Cursor cursor = db.rawQuery(query_sql, new String[]{String.valueOf(teacherID)});

        if (cursor.getCount() == 0) {
            return courses;
        }
        cursor.moveToFirst();
        do {
            int courseID = cursor.getInt(0);
            String courseName = cursor.getString(1);
            String startDate = cursor.getString(2);
            String endDate = cursor.getString(3);
            int tid = cursor.getInt(4);
            Course course = new Course(courseID, courseName, startDate, endDate, tid);
            courses.add(course);
        } while (cursor.moveToNext());

        return courses;
    }
    public Course getCoursesByCourseID(int courseID1) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String query_sql = "SELECT * FROM course WHERE courseID = ?";
        Cursor cursor = db.rawQuery(query_sql, new String[]{String.valueOf(courseID1)});
        Course course = null;
        if (cursor.getCount() == 0) {
            return course;
        }
        cursor.moveToFirst();
        do {
            int courseID = cursor.getInt(0);
            String courseName = cursor.getString(1);
            String startDate = cursor.getString(2);
            String endDate = cursor.getString(3);
            int tid = cursor.getInt(4);
            course = new Course(courseID, courseName, startDate, endDate, tid);
        } while (cursor.moveToNext());

        return course;
    }
}
