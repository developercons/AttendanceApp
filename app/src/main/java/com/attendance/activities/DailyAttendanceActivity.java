package com.attendance.activities;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.attendance.R;
import com.attendance.adapters.AttendanceListAdapter;
import com.attendance.adapters.CourseListAdapter;
import com.attendance.data_models.Student;
import com.attendance.database.MyDBHelper;
import com.attendance.helper_classes.ConstantsString;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DailyAttendanceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btn_submit;
    MyDBHelper dbHelper;
    private ArrayList<Student> studentList = new ArrayList<>();
    String courseName;
    HashMap<String, AttendanceListAdapter.DailyData> dataHashMap = new HashMap<>();
    int presentCounter = 0;
    int absentCounter = 0;
    int sickCounter = 0;
    int lateCounter = 0;
    int total = 0;
    int year, month, day;

    public class AttendanceData {
        public String course;
        public String studentName;
        public String studentEmail;
        public String day;
        public String month;
        public String year;
        public String totalStudent;
        public String attendanceStatus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_attendance);
        dbHelper = MyDBHelper.getInstance(this);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        if(getIntent().getExtras() != null) {
            courseName = getIntent().getStringExtra("COURSE_NAME");
        }
        initViews();
        setUpRecyclerView();

        if(!dbHelper.getStudentList(courseName).isEmpty()) {
            studentList.clear();
            studentList.addAll(dbHelper.getStudentList(courseName));
            RecyclerView.Adapter adapter = new AttendanceListAdapter(this, studentList);
            recyclerView.setAdapter(adapter);
        }

        btn_submit.setOnClickListener(v -> {
            if(dataHashMap.size() == studentList.size()) {
                getAllCounters();
                showDialogBox();
            } else {
                ConstantsString.showAlertDialog(this, "Please mark attendance of all Students");
            }
        });
    }

    private void getAllCounters() {
        presentCounter = 0;
        absentCounter = 0;
        sickCounter = 0;
        lateCounter = 0;
        for(String key : dataHashMap.keySet()) {
            String status = dataHashMap.get(key).getAttendanceStatus();
            if(status.equalsIgnoreCase("present")) {
                presentCounter++;
            } else if(status.equalsIgnoreCase("absent")) {
                absentCounter++;
            } else if(status.equalsIgnoreCase("late")) {
                lateCounter++;
            } else if(status.equalsIgnoreCase("sick")) {
                sickCounter++;
            }
        }
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btn_submit = (Button) findViewById(R.id.btn_submit);
    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void getStudentInfo(AttendanceListAdapter.DailyData data) {
        dataHashMap.put(data.getStudentEmail(), data);
    }

    private void showDialogBox() {
        // custom dialog
        final Dialog dialog = new Dialog(DailyAttendanceActivity.this);
        dialog.setContentView(R.layout.attendance_dialog);

        // set the custom dialog components - text, image and button
        TextView present = (TextView) dialog.findViewById(R.id.tv_present);
        TextView absent = (TextView) dialog.findViewById(R.id.tv_absent);
        TextView late = (TextView) dialog.findViewById(R.id.tv_late);
        TextView sick = (TextView) dialog.findViewById(R.id.tv_sick);
        TextView totalStudent = (TextView) dialog.findViewById(R.id.tv_totalStudent);

        present.setText(String.valueOf(presentCounter));
        absent.setText(String.valueOf(absentCounter));
        late.setText(String.valueOf(lateCounter));
        sick.setText(String.valueOf(sickCounter));
        total = presentCounter + absentCounter + lateCounter + sickCounter;
        totalStudent.setText(String.valueOf(total));

        Button dialogButton = dialog.findViewById(R.id.dialog_btn);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttendanceData data = new AttendanceData();
                for(String hashMapKey: dataHashMap.keySet()) {
                    data.course = courseName;
                    data.studentName = dataHashMap.get(hashMapKey).getStudentName();
                    data.studentEmail = dataHashMap.get(hashMapKey).getStudentEmail();
                    data.attendanceStatus = dataHashMap.get(hashMapKey).getAttendanceStatus();
                    data.totalStudent = totalStudent.getText().toString().trim();
                    data.day = String.valueOf(day);
                    data.month = String.valueOf(month);
                    data.year = String.valueOf(year);
                    boolean check = MyDBHelper.getInstance(DailyAttendanceActivity.this).insertAttendanceData(data);

                    if(check) {
                        ConstantsString.showAlertDialog(DailyAttendanceActivity.this, "Data Inserted");
                    } else {
                        ConstantsString.showAlertDialog(DailyAttendanceActivity.this, "Data Insertion Failed");
                    }
                }
                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }
}
