package com.attendance.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.attendance.R;
import com.attendance.activities.DailyAttendanceActivity;
import com.attendance.data_models.Student;

import java.util.ArrayList;

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.MyViewHolder> {

    private ArrayList<Student> attendanceList;
    private Context context;
    public AttendanceListAdapter(Context context, ArrayList<Student> data) {
        this.context = context;
        attendanceList = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.attendance_list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.tvStudentName.setText(attendanceList.get(i).studentName);
        holder.rg.setOnCheckedChangeListener((group, checkedId) -> {
            DailyData data = new DailyData();
            switch (group.getCheckedRadioButtonId()){
                case R.id.r1:
                    data.setStudentName(attendanceList.get(i).studentName);
                    data.setStudentEmail(attendanceList.get(i).studentEmail);
                    data.setAttendanceStatus(holder.r1.getText().toString());
                    ((DailyAttendanceActivity)context).getStudentInfo(data);
                    break;

                case R.id.r2:
                    data.setStudentName(attendanceList.get(i).studentName);
                    data.setStudentEmail(attendanceList.get(i).studentEmail);
                    data.setAttendanceStatus(holder.r2.getText().toString());
                    ((DailyAttendanceActivity)context).getStudentInfo(data);
                    break;

                case R.id.r3:
                    data.setStudentName(attendanceList.get(i).studentName);
                    data.setStudentEmail(attendanceList.get(i).studentEmail);
                    data.setAttendanceStatus(holder.r3.getText().toString());
                    ((DailyAttendanceActivity)context).getStudentInfo(data);
                    break;

                case R.id.r4:
                    data.setStudentName(attendanceList.get(i).studentName);
                    data.setStudentEmail(attendanceList.get(i).studentEmail);
                    data.setAttendanceStatus(holder.r4.getText().toString());
                    ((DailyAttendanceActivity)context).getStudentInfo(data);
                    break;
            }
        });

    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName;
        RadioButton r1, r2, r3, r4;
        RadioGroup rg;
        private MyViewHolder(View view) {
            super(view);
            tvStudentName = view.findViewById(R.id.tvStudentName);
            r1 = view.findViewById(R.id.r1);
            r2 = view.findViewById(R.id.r2);
            r3 = view.findViewById(R.id.r3);
            r4 = view.findViewById(R.id.r4);
            rg = view.findViewById(R.id.rg);
        }
    }

    public class DailyData {
        private String studentName;
        private String studentEmail;
        private String attendanceStatus;

        void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getStudentName() {
            return studentName;
        }

        void setStudentEmail(String studentEmail) {
            this.studentEmail = studentEmail;
        }

        public String getStudentEmail() {
            return studentEmail;
        }

        public String getAttendanceStatus() {
            return attendanceStatus;
        }

        void setAttendanceStatus(String attendanceStatus) {
            this.attendanceStatus = attendanceStatus;
        }
    }
}
