package com.example.fedunistudentattendance30395713.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fedunistudentattendance30395713.R;
import com.example.fedunistudentattendance30395713.adapter.AttendanceListAdapter;
import com.example.fedunistudentattendance30395713.bean.AttendanceRecord;
import com.example.fedunistudentattendance30395713.bean.Student;
import com.example.fedunistudentattendance30395713.sqlite.AttendanceDao;
import com.example.fedunistudentattendance30395713.sqlite.StudentDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {
    private ListView listView;
    private Button select;
    private AttendanceDao attendanceDao;
    private List<AttendanceRecord> attendanceRecordList = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private AttendanceListAdapter adapter;
    private StudentDao studentDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        attendanceDao = new AttendanceDao(this);
        studentDao = new StudentDao(this);

        listView = findViewById(R.id.listView);
        adapter = new AttendanceListAdapter(this,attendanceRecordList);
        listView.setAdapter(adapter);


        select = findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the text on the button after you select the date
                        String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        select.setText(selectedDate);
                        attendanceRecordList.clear();
                        List<AttendanceRecord> ar = attendanceDao.getAttendanceRecordsByDate(selectedDate);
                        for (int i = 0; i < ar.size(); i++) {
                            if (areDatesEqual(ar.get(i).getDate().trim(),(selectedDate.trim()))){
                                attendanceRecordList.add(ar.get(i));
                            }
                        }
//                        attendanceRecordList.addAll(ar);

//                        for (int i = 0; i < ar.size(); i++) {
//                            try {
//                                if (areDatesInSameWeek(ar.get(i).getDate(),(selectedDate)))
//                                    attendanceRecordList.add(ar.get(i));
//                            } catch (ParseException e) {
//                                Toast.makeText(SearchActivity.this, "？？", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//
//                        Set<Integer> uniqueCourses = extractUniqueCourses(attendanceRecordList);
//
//                        //Get all students
//                        students.clear();
//                        for (int course : uniqueCourses) {
//                            List<Student> st=studentDao.query(course);
//                            students.addAll(st);
//
//                        }
//
//                        int k =students.size()-1;
//                        for (int i = k; i >=0; i--) {
//                            for (int j = 0; j < attendanceRecordList.size(); j++) {
//                                if (students.get(i).getStudentID() == attendanceRecordList.get(j).getStudentID()&&
//                                        students.get(i).getCourseID() == attendanceRecordList.get(j).getCourseID()){
//                                    students.remove(i);
//                                }
//                            }
//                        }
//                        for (int i = 0; i < students.size(); i++) {
//                            attendanceRecordList.add(new AttendanceRecord(-1,selectedDate,students.get(i).getStudentID(),"0",students.get(i).getCourseID()));
//                        }

                        adapter.notifyDataSetChanged();

                    }
                }, year, month, day);

        datePickerDialog.show();
    }
    public static Set<Integer> extractUniqueCourses(List<AttendanceRecord> attendanceRecordList) {
        Set<Integer> uniqueCourses = new HashSet<>();

        for (AttendanceRecord record : attendanceRecordList) {
            uniqueCourses.add(record.getCourseID());
        }

        return uniqueCourses;
    }
    //Determine whether the two dates are the same week
    public static boolean areDatesInSameWeek(String date1, String date2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj1 = sdf.parse(date1);
        Date dateObj2 = sdf.parse(date2);

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(dateObj1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(dateObj2);

        int week1 = cal1.get(Calendar.WEEK_OF_YEAR);
        int week2 = cal2.get(Calendar.WEEK_OF_YEAR);

        return week1 == week2;
    }
    public boolean areDatesEqual(String dateString1, String dateString2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date date1 = sdf.parse(dateString1);
            Date date2 = sdf.parse(dateString2);

            return date1.equals(date2);
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Return an error code or throw an exception, depending on the situation
        }
    }

}