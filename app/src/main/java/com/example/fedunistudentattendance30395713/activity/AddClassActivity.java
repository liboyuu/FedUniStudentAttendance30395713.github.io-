package com.example.fedunistudentattendance30395713.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.fedunistudentattendance30395713.MainData;
import com.example.fedunistudentattendance30395713.R;
import com.example.fedunistudentattendance30395713.sqlite.CourseDao;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AddClassActivity extends AppCompatActivity {
    private TextInputEditText courseNameEditText;
    private Button startDateButton;
    private Button backButton;

    private Button finishButton;
    private int teacher_id=-1;

    private CourseDao courseDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        courseDao = new CourseDao(this);

        courseNameEditText = findViewById(R.id.tv_id);
        startDateButton = findViewById(R.id.button);
        backButton = findViewById(R.id.button2);
        finishButton = findViewById(R.id.finish);

        teacher_id = MainData.teacher.getId();

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Submit add course
                String courseName = courseNameEditText.getText().toString();
                String startDate = startDateButton.getText().toString();

                // Verify that the course name and start date meet the requirements
                if (TextUtils.isEmpty(courseName) || !startDate.contains("-")) {
                    // Prompt the user to complete the information
                    Toast.makeText(AddClassActivity.this, "please input msg", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Add the course information to the database here
                // You can do this using the corresponding methods in CourseDao
                 courseDao.insertCourse(courseName, startDate, teacher_id);

                // A message is displayed indicating that the course is added successfully
                Toast.makeText(AddClassActivity.this, "course add success", Toast.LENGTH_SHORT).show();

                // End the current Activity
                finish();
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
                        startDateButton.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }
}