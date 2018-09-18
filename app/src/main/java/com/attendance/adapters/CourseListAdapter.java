package com.attendance.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.attendance.R;
import com.attendance.activities.DailyAttendanceActivity;

import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.MyViewHolder> {

    private ArrayList<String> courseList;
    private Context context;

    public CourseListAdapter(Context context, ArrayList< String > data) {
        this.context = context;
        courseList = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.tvCourseName.setText(courseList.get(i));
        holder.tvCourseName.setOnClickListener(v -> {
            Intent intent = new Intent(context, DailyAttendanceActivity.class);
            intent.putExtra("COURSE_NAME", courseList.get(i));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName;
        private MyViewHolder(View view) {
            super(view);
            tvCourseName = view.findViewById(R.id.tvCourseName);
        }
    }
}