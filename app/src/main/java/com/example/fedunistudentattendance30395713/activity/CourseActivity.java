package com.example.fedunistudentattendance30395713.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fedunistudentattendance30395713.R;
import com.example.fedunistudentattendance30395713.adapter.CourseAdapter;
import com.example.fedunistudentattendance30395713.adapter.StudentListAdapter;
import com.example.fedunistudentattendance30395713.bean.AttendanceRecord;
import com.example.fedunistudentattendance30395713.bean.Course;
import com.example.fedunistudentattendance30395713.bean.Student;
import com.example.fedunistudentattendance30395713.sqlite.AttendanceDao;
import com.example.fedunistudentattendance30395713.sqlite.CourseDao;
import com.example.fedunistudentattendance30395713.sqlite.StudentDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseActivity extends AppCompatActivity {
    private Button delteButton;
    public static Course course;
    private CourseDao courseDao;
    private List<String> dates = new ArrayList<>();
    private String sp_date=course.getStartDate();

    private ListView listView;
    private StudentListAdapter studentListAdapter;
    private List<Student> students = new ArrayList<>();
    private StudentDao studentDao;
    private AttendanceDao attendanceDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        courseDao = new CourseDao(this);
        studentDao = new StudentDao(this);
        attendanceDao = new AttendanceDao(this);

        delteButton = findViewById(R.id.del);



        delteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseDao.deleteCourseById(course.getCourseID());
                finish();
            }
        });

        initSpinnerData(course.getStartDate());

        initSpinner();

        initList(sp_date);

    }

    private void initList(String date) {


        listView = findViewById(R.id.listView);
        studentListAdapter = new StudentListAdapter(this,students,date,course.getCourseID(),course.getCourseName());
        listView.setAdapter(studentListAdapter);

        List<Student> st=studentDao.query(course.getCourseID());
        students.clear();
        students.addAll(st);

        studentListAdapter.notifyDataSetChanged();

    }


    private void initSpinner() {
        Spinner spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter to set up the data
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dates);
        // Set the style of the drop-down menu
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Bind the adapter to the Spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                sp_date = dates.get(i);
                initList(sp_date);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initSpinnerData(String startDate) {

        // Gets the Calendar object for the current date
        Calendar calendar = Calendar.getInstance();

        // Set the current date to the start date of the course
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date start = sdf.parse(startDate);
            calendar.setTime(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Cycle to add dates for 12 consecutive weeks
        for (int i = 0; i < 12; i++) {
            // Gets the year, month, and day of the current date
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // Note that the month starts at 0 and needs to be increased by 1
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Format the date as a string
            String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day);

            // Add to the list
            dates.add(formattedDate);

            // Add a week to the date
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_stu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        showAddStudentDialog();

        return super.onOptionsItemSelected(item);
    }
    public void showAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_student, null);

        // Gets the control in the dialog box
        EditText studentIDCardEditText = dialogView.findViewById(R.id.edit_text_student_id_card);
        EditText nameEditText = dialogView.findViewById(R.id.edit_text_name);
        Button addButton = dialogView.findViewById(R.id.button_add);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input student information
                String studentIDCard = studentIDCardEditText.getText().toString();
                String name = nameEditText.getText().toString();
                // Add student information to the database here
                studentDao.insert(new Student(0,studentIDCard, name,course.getCourseID()));


                for (int i = 0; i < dates.size(); i++) {
                    List<Student> stus = studentDao.query(course.getCourseID());
                    Student stu = null;
                    for (int j = 0; j < stus.size(); j++) {
                        if (stus.get(j).getStudentIDCard().equals(studentIDCard)){
                            stu = stus.get(j);
                            break;
                        }
                    }
                    // Create the AttendanceRecord object and set the corresponding properties
                    AttendanceRecord attendanceRecord = new AttendanceRecord(0,dates.get(i),stu.getStudentID(),"0",course.getCourseID());
                    // Add attendance records
                    attendanceDao.insert(attendanceRecord);
                }
                // Close dialog
                dialog.dismiss();
                Toast.makeText(CourseActivity.this, "add finish", Toast.LENGTH_SHORT).show();

                initList(sp_date);
            }
        });

        dialog.show();
    }

}