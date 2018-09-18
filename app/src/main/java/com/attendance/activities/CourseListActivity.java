package com.attendance.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.attendance.R;
import com.attendance.adapters.CourseListAdapter;
import com.attendance.database.MyDBHelper;

import java.util.ArrayList;

public class CourseListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyDBHelper dbHelper;
    private ArrayList<String> courseList = new ArrayList<>();
    String teacherEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        dbHelper = MyDBHelper.getInstance(this);

        if(getIntent().getExtras() != null) {
           teacherEmail = getIntent().getStringExtra("TEACHER_EMAIL");
        }
        initViews();
        setUpRecyclerView();

        if(!dbHelper.getCourseList(teacherEmail).isEmpty()) {
            courseList.clear();
            courseList.addAll(dbHelper.getCourseList(teacherEmail));
            RecyclerView.Adapter adapter = new CourseListAdapter(this, courseList);
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
