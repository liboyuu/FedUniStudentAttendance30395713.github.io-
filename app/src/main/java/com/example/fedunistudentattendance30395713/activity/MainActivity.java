package com.example.fedunistudentattendance30395713.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.fedunistudentattendance30395713.MainData;
import com.example.fedunistudentattendance30395713.R;
import com.example.fedunistudentattendance30395713.adapter.CourseAdapter;
import com.example.fedunistudentattendance30395713.bean.Course;
import com.example.fedunistudentattendance30395713.sqlite.CourseDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private CourseAdapter courseAdapter;
    private CourseDao courseDao;
    private List<Course> courses = new ArrayList<>();
    private Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Course List");
        setContentView(R.layout.activity_main);


        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Initialize the CourseDao
        courseDao = new CourseDao(this);
        int teacherID = MainData.teacher.getId();
        // Get course list
        List<Course> courses1 = courseDao.getCoursesByTeacherID(teacherID);

        // Initialize the ListView and Adapter
        courses.clear();
        courses.addAll(courses1);
        listView = findViewById(R.id.list_view);
        courseAdapter = new CourseAdapter(this, courses);
        listView.setAdapter(courseAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CourseActivity.course = courses.get(i);
                Intent intent = new Intent(MainActivity.this,CourseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(MainActivity.this,AddClassActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }


}