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
import com.attendance.data_models.ClassData;
import com.attendance.fragments.ClassDetailsFragment;
import com.attendance.fragments.ClassDialogFragment;

import java.util.ArrayList;

public class EditClassDetailsAdapter extends RecyclerView.Adapter<
		EditClassDetailsAdapter.MyViewHolder> {

	private ArrayList<ClassData> classData;
	private Context context;
	private ClassDetailsFragment fragment;

	public EditClassDetailsAdapter(Context context, ArrayList<ClassData> data) {
		this.context = context;
		classData = data;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.edit_class_details, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
		holder.tvTeacherName.setText(classData.get(i).getTeacherName());
		holder.tvClassName.setText(classData.get(i).getClassName());
		holder.btnEdit.setOnClickListener(v -> {
			FragmentManager manager = ((ViewDetailsActivity)context).getSupportFragmentManager();
			ClassDialogFragment dialogFragment = new ClassDialogFragment();
			dialogFragment.show(manager, ClassDialogFragment.TAG);
		});
	}

	@Override
	public int getItemCount() {
		return classData.size();
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {
		TextView tvTeacherName;
		TextView tvClassName;
		Button btnEdit;
		private MyViewHolder(View view) {
			super(view);
			tvTeacherName = view.findViewById(R.id.tvTeacherName);
			tvClassName = view.findViewById(R.id.tvClassName);
			btnEdit = view.findViewById(R.id.btnEdit);
		}
	}

	private void toast(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
