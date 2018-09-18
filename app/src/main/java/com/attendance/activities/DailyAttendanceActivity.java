package com.attendance.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.attendance.R;
import com.attendance.adapters.AttendanceListAdapter;
import com.attendance.adapters.CourseListAdapter;
import com.attendance.database.MyDBHelper;

import java.util.ArrayList;

public class DailyAttendanceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDBHelper dbHelper;
    private ArrayList<String> studentList = new ArrayList<>();
    String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_attendance);
        dbHelper = MyDBHelper.getInstance(this);

        if(getIntent().getExtras() != null) {
            courseName = getIntent().getStringExtra("COURSE_NAME");
        }
        initViews();
        setUpRecyclerView();

        if(!dbHelper.getCourseList(courseName).isEmpty()) {
            studentList.clear();
            studentList.addAll(dbHelper.getCourseList(courseName));
            RecyclerView.Adapter adapter = new AttendanceListAdapter(this, studentList);
            recyclerView.setAdapter(adapter);
        }
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.rcvCourseList);
    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
