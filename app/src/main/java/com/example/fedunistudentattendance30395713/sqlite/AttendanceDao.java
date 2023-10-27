package com.example.fedunistudentattendance30395713.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fedunistudentattendance30395713.bean.AttendanceRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttendanceDao {
    private DbHelper mHelper;

    public AttendanceDao(Context context) {
        mHelper = new DbHelper(context);
    }

    public void insert(AttendanceRecord record) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String insert_sql = "INSERT INTO attendance (date, studentID, attendanceStatus,courseID) VALUES (?,?,?,?)";
        Object[] obj = {record.getDate(), record.getStudentID(), record.getAttendanceStatus(),record.getCourseID()};
        db.execSQL(insert_sql, obj);
        db.close();
    }
    public void updateAttendanceStatus(String date,int recordId, String newStatus) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String update_sql = "UPDATE attendance SET attendanceStatus = ? WHERE date = ? and studentID=? ";
        Object[] obj = {newStatus, date,recordId};
        db.execSQL(update_sql, obj);
        db.close();
    }

    public void deleteByCourseAndDate(int courseId, String date) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String delete_sql = "DELETE FROM attendance WHERE studentID = ? AND date = ?";
        db.execSQL(delete_sql, new Object[]{courseId, date});
        db.close();
    }
    public boolean hasAttendanceRecord(int studentID, String date) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String query_sql = "SELECT * FROM attendance WHERE studentID = ? AND date = ? and attendanceStatus='1'";
        Cursor cursor = db.rawQuery(query_sql, new String[]{String.valueOf(studentID), date});

        boolean hasRecord = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return hasRecord;
    }
    //Determine if the third date is between the first date and the second date
    public static boolean isDateBetween(String startDate, String endDate, String targetDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDateObj = sdf.parse(startDate);
        Date endDateObj = sdf.parse(endDate);
        Date targetDateObj = sdf.parse(targetDate);

        return targetDateObj.after(startDateObj) && targetDateObj.before(endDateObj);
    }
//    public List<AttendanceRecord> getAttendanceRecordsByDate(String date) {
//        List<AttendanceRecord> attendanceRecords = new ArrayList<>();
//        SQLiteDatabase db = mHelper.getReadableDatabase();
//        String query_sql = "SELECT * FROM attendance where date = ?";
//        Cursor cursor = db.rawQuery(query_sql, new String[]{ date});
//
//        if (cursor.moveToFirst()) {
//            do {
//                int recordId = cursor.getInt(0);
//                String recordDate = cursor.getString(1);
//                int studentID = cursor.getInt(2);
//                String attendanceStatus = cursor.getString(3);
//                int courseID = cursor.getInt(4);
//
//                AttendanceRecord record = new AttendanceRecord(recordId, recordDate, studentID, attendanceStatus,courseID);
//                attendanceRecords.add(record);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//
//        return attendanceRecords;
//    }
public List<AttendanceRecord> getAttendanceRecordsByDate(String date) {
    List<AttendanceRecord> attendanceRecords = new ArrayList<>();
    SQLiteDatabase db = mHelper.getReadableDatabase();
    String query_sql = "SELECT * FROM attendance";
    Cursor cursor = db.rawQuery(query_sql, null);

    if (cursor.moveToFirst()) {
        do {
            int recordId = cursor.getInt(0);
            String recordDate = cursor.getString(1);
            int studentID = cursor.getInt(2);
            String attendanceStatus = cursor.getString(3);
            int courseID = cursor.getInt(4);

            AttendanceRecord record = new AttendanceRecord(recordId, recordDate, studentID, attendanceStatus,courseID);
            attendanceRecords.add(record);
        } while (cursor.moveToNext());
    }

    cursor.close();
    db.close();

    return attendanceRecords;
}
}
