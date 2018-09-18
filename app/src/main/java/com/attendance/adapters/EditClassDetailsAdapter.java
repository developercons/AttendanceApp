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
import com.attendance.data_models.ClassData;
import com.attendance.fragments.ClassDetailsFragment;
import com.attendance.fragments.ClassDialogFragment;
import com.attendance.fragments.TeacherDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class EditClassDetailsAdapter extends RecyclerView.Adapter<
		EditClassDetailsAdapter.MyViewHolder> {

	private ArrayList<ClassData > dataList;
	private Context context;

	public EditClassDetailsAdapter(Context context, ArrayList< ClassData > data) {
		this.context = context;
		dataList = data;
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
		holder.tvCourseName.setText(dataList.get(i).getCourseName());
		holder.tvSemester.setText(dataList.get(i).getSemester());
		holder.btnEdit.setOnClickListener(v -> {
			RowData data = new RowData();
			FragmentManager manager = ((ViewDetailsActivity)context).getSupportFragmentManager();
			List<Fragment> fragList = manager.getFragments();
			ClassDetailsFragment fragment = null;
			for ( Fragment frag : fragList ) {
				if (frag instanceof ClassDetailsFragment) {
					fragment = (ClassDetailsFragment) frag;
				}
			}
			if (fragment != null) {
				ClassDialogFragment dialogFragment = new ClassDialogFragment();
				dialogFragment.setTargetFragment(fragment, 2);
				dialogFragment.show(manager, ClassDialogFragment.TAG);
				data.setCourseName(holder.tvCourseName.getText().toString());
				data.setSemester(holder.tvSemester.getText().toString());
				data.setPosition(String.valueOf(i));
				dialogFragment.rowClassData(data);
			}
		});
	}

	@Override
	public int getItemCount() {
		return dataList.size();
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {
		TextView tvCourseName;
		TextView tvSemester;
		Button btnEdit;
		private MyViewHolder(View view) {
			super(view);
			tvCourseName = view.findViewById(R.id.tvCourseName);
			tvSemester = view.findViewById(R.id.tvSemester);
			btnEdit = view.findViewById(R.id.btnEdit);
		}
	}

	public class RowData {
		private String courseName;
		private String semester;
		private String position;

		public String getCourseName() {
			return courseName;
		}

		public String getSemester() {
			return semester;
		}

		public String getPosition() {
			return position;
		}

		void setCourseName(String courseName) {
			this.courseName = courseName;
		}

		void setSemester(String semester) {
			this.semester = semester;
		}

		void setPosition(String position) {
			this.position = position;
		}
	}

	private void toast(String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
