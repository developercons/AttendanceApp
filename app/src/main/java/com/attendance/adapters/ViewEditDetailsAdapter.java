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
import com.attendance.data_models.DataModel;
import com.attendance.fragments.ClassDialogFragment;
import com.attendance.fragments.StudentDialogFragment;
import com.attendance.fragments.StudentViewReportFragment;
import com.attendance.fragments.TeacherDialogFragment;

import java.util.ArrayList;

public class ViewEditDetailsAdapter extends RecyclerView.Adapter<
		ViewEditDetailsAdapter.MyViewHolder> {

	private String[] tags= {"TEACHER_DETAILS", "CLASS_DETAILS", "STUDENT_DETAILS"};
	private ArrayList<DataModel > dataList;
	private Context context;
	private String tag = "";

	public ViewEditDetailsAdapter(Context context, ArrayList< DataModel > data, String tag) {
		this.context = context;
		dataList = data;
		this.tag = tag;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.view_edit_details, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
		holder.tvTeacherName.setText(dataList.get(i).getTeacherName());
		holder.tvClassName.setText(dataList.get(i).getClassName());
		holder.btnEdit.setOnClickListener(v -> {
			FragmentManager manager = ((ViewDetailsActivity)context).getSupportFragmentManager();
			if ( tag.equals(tags[ 0 ]) ) {
				TeacherDialogFragment dialogFragment = new TeacherDialogFragment();
				dialogFragment.show(manager, TeacherDialogFragment.TAG);
			}
			else if ( tag.equals(tags[ 1 ]) ) {
				ClassDialogFragment dialogFragment = new ClassDialogFragment();
				dialogFragment.show(manager, ClassDialogFragment.TAG);
			}
			else if ( tag.equals(tags[ 2 ]) ) {
				StudentDialogFragment dialogFragment = new StudentDialogFragment();
				dialogFragment.show(manager, StudentDialogFragment.TAG);
			}
			else {
				toast("No dialog fragment found");
			}
		});
	}

	@Override
	public int getItemCount() {
		return dataList.size();
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
