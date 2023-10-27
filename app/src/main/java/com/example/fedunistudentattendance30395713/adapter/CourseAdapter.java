package com.example.fedunistudentattendance30395713.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fedunistudentattendance30395713.bean.Course;

import com.example.fedunistudentattendance30395713.R;
import java.util.List;

public class CourseAdapter extends BaseAdapter {
    private List<Course> courses;
    private Context context;

    public CourseAdapter(Context context, List<Course> courses) {
        this.courses = courses;
        this.context = context;
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int position) {
        return courses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.course_item, null);

        // Gets the TextView in the layout
        TextView courseNameTextView = view.findViewById(R.id.course_name);
        TextView startDateTextView = view.findViewById(R.id.start_date);
        TextView endDateTextView = view.findViewById(R.id.end_date);

        // Gets the course object corresponding to the current location
        Course course = courses.get(position);

        // Set the course information to the corresponding TextView
        courseNameTextView.setText("CourseName：" + course.getCourseName());
        startDateTextView.setText("Start Date：" + course.getStartDate());
        endDateTextView.setText("End Date：" + course.getEndDate());


        return view;
    }

}
