package com.example.fedunistudentattendance30395713.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fedunistudentattendance30395713.bean.User;

import java.util.ArrayList;
import java.util.List;


public class UserDao {
    private DbHelper mHelper;

    public UserDao(Context context) {
        mHelper = new DbHelper(context);
    }

    public void insert(User userbean) {
        SQLiteDatabase db = mHelper.getWritableDatabase();


        String insert_sql = "INSERT INTO user (username,password) VALUES (?,?)";
        Object[] obj = {userbean.getUsername(), userbean.getPassword()};
        db.execSQL(insert_sql, obj);
        db.close();
    }

    public List<User> query() {

        SQLiteDatabase db = mHelper.getReadableDatabase();
        List<User> userbeans = new ArrayList<User>();
        String query_sql = "select * from user";
        Cursor cursor = db.rawQuery(query_sql, null);

        if (0 == cursor.getCount()) {
            return null;
        }
        cursor.moveToFirst();
        do {
            int id = cursor.getInt(0);
            String a = cursor.getString(1);
            String b = cursor.getString(2);
            User userbean = new User(id, a, b);
            userbeans.add(userbean);
        }while (cursor.moveToNext());


        return userbeans;
    }


}
