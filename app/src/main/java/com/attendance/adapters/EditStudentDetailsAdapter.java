package com.attendance.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.attendance.fragments.StudentDetailsFragment;
import com.attendance.fragments.StudentDialogFragment;

import java.util.ArrayList;
import java.util.List;

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
			List<Fragment> fragList = manager.getFragments();
			StudentDetailsFragment fragment = null;
			for ( Fragment frag : fragList ) {
				if (frag instanceof StudentDetailsFragment) {
					fragment = (StudentDetailsFragment) frag;
				}
			}
			if (fragment != null) {
				RowStudentData data = new RowStudentData();
				data.setStudentName(dataList.get(i).getStudentName());
				data.setClassName(dataList.get(i).getClassName());
				data.setStudentEmailId(dataList.get(i).getStudentEmailId());
				data.setPosition(String.valueOf(i));

				StudentDialogFragment dialogFragment = new StudentDialogFragment();
				dialogFragment.setTargetFragment(fragment, 3);
				dialogFragment.rowData(data);
				dialogFragment.show(manager, StudentDialogFragment.TAG);
			}
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

	public class RowStudentData {
		private String studentName;
		private String className;
		private String studentEmailId;
		private String position;

		public String getStudentName() {
			return studentName;
		}

		public void setStudentName(String studentName) {
			this.studentName = studentName;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public String getStudentEmailId() {
			return studentEmailId;
		}

		public void setStudentEmailId(String studentEmailId) {
			this.studentEmailId = studentEmailId;
		}
	}

	private void toast(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}