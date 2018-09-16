package com.attendance.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.attendance.R;
import com.attendance.activities.ViewDetailsActivity;
import com.attendance.data_models.TeacherData;
import com.attendance.fragments.TeacherDialogFragment;

import java.util.ArrayList;

public class EditTeacherDetailsAdapter extends RecyclerView.Adapter<
		EditTeacherDetailsAdapter.MyViewHolder> {

	private ArrayList<TeacherData> dataList;
	private Context context;

	public EditTeacherDetailsAdapter(Context context, ArrayList< TeacherData > data) {
		this.context = context;
		dataList = data;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.edit_teacher_details, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
		holder.tvTeacherName.setText(dataList.get(i).getTeacherName());
		holder.tvCourseName.setText(dataList.get(i).getCourseName());
		holder.btnEdit.setOnClickListener(v -> {
			FragmentManager manager = ((ViewDetailsActivity )context).getSupportFragmentManager();
			TeacherDialogFragment dialogFragment = new TeacherDialogFragment();
			dialogFragment.show(manager, TeacherDialogFragment.TAG);
		});
	}

	@Override
	public int getItemCount() {
		return dataList.size();
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {
		TextView tvTeacherName;
		TextView tvCourseName;
		Button btnEdit;
		private MyViewHolder(View view) {
			super(view);
			tvTeacherName = view.findViewById(R.id.tvTeacherName);
			tvCourseName = view.findViewById(R.id.tvCourseName);
			btnEdit = view.findViewById(R.id.btnEdit);
		}
	}


	private void toast(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}

