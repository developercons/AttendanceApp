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
import com.attendance.data_models.StudentData;
import com.attendance.fragments.StudentDialogFragment;

import java.util.ArrayList;

public class EditStudentDetailsAdapter extends RecyclerView.Adapter<
		EditStudentDetailsAdapter.MyViewHolder> {

	private ArrayList<StudentData> dataList;
	private Context context;

	public EditStudentDetailsAdapter(Context context, ArrayList< StudentData > data) {
		this.context = context;
		dataList = data;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.edit_student_details, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
		holder.tvStudentName.setText(dataList.get(i).getStudentName());
		holder.tvClassName.setText(dataList.get(i).getClassName());
		holder.btnEdit.setOnClickListener(v -> {
			FragmentManager manager = ((ViewDetailsActivity )context).getSupportFragmentManager();
			StudentDialogFragment dialogFragment = new StudentDialogFragment();
			dialogFragment.show(manager, StudentDialogFragment.TAG);
		});
	}

	@Override
	public int getItemCount() {
		return dataList.size();
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {
		TextView tvStudentName;
		TextView tvClassName;
		Button btnEdit;
		private MyViewHolder(View view) {
			super(view);
			tvStudentName = view.findViewById(R.id.tvStudentName);
			tvClassName = view.findViewById(R.id.tvClassName);
			btnEdit = view.findViewById(R.id.btnEdit);
		}
	}


	private void toast(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}