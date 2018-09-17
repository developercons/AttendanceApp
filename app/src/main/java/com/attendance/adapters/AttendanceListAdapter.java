package com.attendance.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.attendance.R;

import java.util.ArrayList;

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.MyViewHolder> {

    private ArrayList<String> attendanceList;
    private Context context;

    public AttendanceListAdapter(Context context, ArrayList< String > data) {
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
        holder.tvStudentName.setText(attendanceList.get(i));
        holder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

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
}
