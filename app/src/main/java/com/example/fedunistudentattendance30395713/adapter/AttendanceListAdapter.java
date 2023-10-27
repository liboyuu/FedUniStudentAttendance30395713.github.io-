package com.example.fedunistudentattendance30395713.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.fedunistudentattendance30395713.R;
import com.example.fedunistudentattendance30395713.bean.AttendanceRecord;
import com.example.fedunistudentattendance30395713.bean.Student;
import com.example.fedunistudentattendance30395713.sqlite.AttendanceDao;
import com.example.fedunistudentattendance30395713.sqlite.CourseDao;
import com.example.fedunistudentattendance30395713.sqlite.StudentDao;

import java.util.List;

public class AttendanceListAdapter extends BaseAdapter {

    private List<AttendanceRecord> attendanceRecordList;
    private LayoutInflater inflater;
    private StudentDao studentDao;
    private CourseDao courseDao;

    private Context context;

    public AttendanceListAdapter(Context context, List<AttendanceRecord> attendanceRecordList) {
        this.attendanceRecordList = attendanceRecordList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return attendanceRecordList.size();
    }

    @Override
    public Object getItem(int position) {
        return attendanceRecordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.attend_item, parent, false);
        }

        studentDao  = new StudentDao(context);
        courseDao = new CourseDao(context);
        TextView studentNameTextView = convertView.findViewById(R.id.student_name);
        TextView studentIdTextView = convertView.findViewById(R.id.student_id);
        TextView studentAttend = convertView.findViewById(R.id.student_attend);
        TextView student_course = convertView.findViewById(R.id.student_course);

        studentNameTextView.setText("Student Name:"+studentDao.getStudentNameById(attendanceRecordList.get(position).getStudentID()));
        studentIdTextView.setText("StudentID:"+attendanceRecordList.get(position).getStudentID());
        studentAttend.setText("attend: " + ((attendanceRecordList.get(position).getAttendanceStatus().equals("1"))?"yes":"no")+attendanceRecordList.get(position).getDate());
        student_course.setText("Course: "+courseDao.getCoursesByCourseID(attendanceRecordList.get(position).getCourseID()).getCourseName());


        return convertView;
    }
}
