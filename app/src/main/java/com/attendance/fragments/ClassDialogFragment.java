package com.attendance.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.attendance.R;
import com.attendance.activities.ViewDetailsActivity;
import com.attendance.custom_classes.CustomAutoCompleteTextView;
import com.attendance.custom_classes.CustomInputEditText;
import com.attendance.custom_classes.CustomSpinner;
import com.attendance.custom_classes.CustomTextInputLayout;

import java.util.ArrayList;

public class ClassDialogFragment extends DialogFragment implements View.OnClickListener {

	public static final String TAG = "CLASS_UPDATE";
	private ViewDetailsActivity activity;
	private ViewGroup parent;
	private CustomTextInputLayout til_courseName,til_semester, til_teacherEmail, til_teacherName;
	private CustomSpinner sp_courseName,sp_semester;
	private CustomAutoCompleteTextView ac_teacherEmail;
	private CustomInputEditText et_teacherName;
	private TextView tvBack;
	private EditClassData data;

	ArrayList<String> list = new ArrayList<>();

	public ClassDialogFragment newInstance(ViewGroup parent) {
		this.parent = parent;
		return new ClassDialogFragment();
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.fragment_add_class, parent, false);
		data = new EditClassData();
		setupView(view);
		builder.setView(view);
		return builder.create();
	}

	private void setupView(View view) {

		if (list.isEmpty()) {
			list.add("A");
			list.add("B");
			list.add("C");
		}

		til_courseName = view.findViewById(R.id.til_courseName);
		til_semester = view.findViewById(R.id.til_semester);
		til_teacherEmail = view.findViewById(R.id.til_teacherEmail);
		til_teacherName = view.findViewById(R.id.til_teacherName);
		sp_courseName = view.findViewById(R.id.sp_courseName);
		sp_semester = view.findViewById(R.id.sp_semester);
		ac_teacherEmail = view.findViewById(R.id.ac_teacherEmail);
		et_teacherName = view.findViewById(R.id.et_teacherName);
		tvBack = view.findViewById(R.id.tvBack);
		Button btnSubmit = view.findViewById(R.id.btn_submit);
		
		// TODO: set focus change listener
		sp_courseName.setFocusChange(til_courseName);
		sp_semester.setFocusChange(til_semester);
		ac_teacherEmail.setFocusChangeEmailId(til_teacherEmail);
		et_teacherName.setFocusChange(til_teacherName);

		//todo: implement click listener
		tvBack.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
		tvBack.setVisibility(View.VISIBLE);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(activity.getApplicationContext(),
				android.R.layout.simple_expandable_list_item_1, list);
		adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

		sp_courseName.setAdapter(adapter);
		sp_semester.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {
		switch ( v.getId() ) {
			case R.id.tvBack:
				dismiss();
				break;

			case R.id.btn_submit:
				if (checkMandatoryFields()) {
					dismiss();
				} else {
					activity.toast(getString(R.string.check_mandatory));
				}
				break;
		}
	}

	public boolean checkMandatoryFields() {
		int totalCount = 4;
		int count = 0;
		data.setCourseName(sp_courseName.toString());
		data.setSemester(sp_semester.toString());
		data.setTeacherEmailId(ac_teacherEmail.toString());
		data.setTeacherName(et_teacherName.toString());

		if (!TextUtils.isEmpty(data.getCourseName())) {
			til_courseName.setEnabled();
			count++;
		} else {
			til_courseName.setErrorMessage();
		}

		if (!TextUtils.isEmpty(data.getSemester())) {
			til_semester.setEnabled();
			count++;
		} else {
			til_semester.setErrorMessage();
		}

		if (!TextUtils.isEmpty(data.getTeacherEmailId())) {
			til_teacherEmail.setEnabled();
			count++;
		} else {
			til_teacherEmail.setErrorMessage();
		}

		if (!TextUtils.isEmpty(data.getTeacherName())) {
			til_teacherName.setEnabled();
			count++;
		} else {
			til_teacherName.setErrorMessage();
		}
		return count != 0 && count == totalCount;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if ( context instanceof ViewDetailsActivity ) {
			activity = (ViewDetailsActivity) context;
		} else {
			throw new ClassCastException("Wrong activity fragment typecast");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		activity = null;
	}

	public class EditClassData{
		private String courseName;
		private String semester;
		private String teacherEmailId;
		private String teacherName;

		public String getCourseName() {
			return courseName;
		}

		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}

		public String getSemester() {
			return semester;
		}

		public void setSemester(String semester) {
			this.semester = semester;
		}

		public String getTeacherEmailId() {
			return teacherEmailId;
		}

		public void setTeacherEmailId(String teacherEmailId) {
			this.teacherEmailId = teacherEmailId;
		}

		public String getTeacherName() {
			return teacherName;
		}

		public void setTeacherName(String teacherName) {
			this.teacherName = teacherName;
		}
	}
}
