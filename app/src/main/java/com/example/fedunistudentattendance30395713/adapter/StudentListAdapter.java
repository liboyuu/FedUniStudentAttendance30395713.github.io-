package com.example.fedunistudentattendance30395713.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fedunistudentattendance30395713.R;
import com.example.fedunistudentattendance30395713.bean.AttendanceRecord;
import com.example.fedunistudentattendance30395713.bean.Student;
import com.example.fedunistudentattendance30395713.sqlite.AttendanceDao;

import java.util.List;

public class StudentListAdapter extends BaseAdapter {

    private List<Student> students;
    private LayoutInflater inflater;
    private AttendanceDao attendanceDao;

    private Context context;
    private String date;
    private int courseid;
    private String course_name;

    public StudentListAdapter(Context context, List<Student> students,String date,int courseid, String course_name) {
        this.students = students;
        this.context = context;
        this.date = date;
        this.courseid = courseid;
        this.course_name = course_name;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.student_item, parent, false);
        }

        TextView studentNameTextView = convertView.findViewById(R.id.student_name);
        TextView studentIdTextView = convertView.findViewById(R.id.student_id);
        TextView student_course = convertView.findViewById(R.id.student_course);

        CheckBox studentCheckbox = convertView.findViewById(R.id.student_checkbox);

        Student student = students.get(position);
        studentNameTextView.setText(student.getName());
        student_course.setText("Course:"+course_name);
        studentIdTextView.setText("ID: " + student.getStudentID());
        attendanceDao = new AttendanceDao(context);

        boolean hasRecord = attendanceDao.hasAttendanceRecord(student.getStudentID(), date);

        if (hasRecord) {
            // Attendance records exist
            studentCheckbox.setChecked(true);
        } else {
            // No attendance records exist
            studentCheckbox.setChecked(false);
        }

        studentCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // Add your attendance record here
                    attendanceDao = new AttendanceDao(context);
                    // Update attendance records
                    attendanceDao.updateAttendanceStatus(date,student.getStudentID(),"1");
                }else {
                    // Add your attendance record here
                    attendanceDao = new AttendanceDao(context);
                    // Update attendance records
                    attendanceDao.updateAttendanceStatus(date,student.getStudentID(),"0");
                }

            }
        });

        return convertView;
    }
}
