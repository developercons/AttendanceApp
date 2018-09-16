package com.attendance.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.attendance.R;
import com.attendance.activities.ViewDetailsActivity;
import com.attendance.custom_classes.CustomInputEditText;
import com.attendance.custom_classes.CustomSpinner;
import com.attendance.custom_classes.CustomTextInputLayout;

import java.util.ArrayList;

public class StudentDialogFragment extends DialogFragment implements View.OnClickListener {

	public static final String TAG = "STUDENT_DETAILS_UPDATE";
	private ViewDetailsActivity activity;
	private ViewGroup parent;
	private CustomTextInputLayout til_studentName, til_class, til_phone, til_email;
	private CustomInputEditText et_studentName, et_phone, et_email;
	private CustomSpinner sp_course;
	private EditStudentData data;
	private boolean isFlags = false;
	ArrayList<String> list = new ArrayList<>();

	public StudentDialogFragment newInstance(ViewGroup parent) {
		this.parent = parent;
		return new StudentDialogFragment();
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.fragment_add_student, parent, false);
		data = new EditStudentData();
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

		til_studentName = view.findViewById(R.id.til_studentName);
		til_class = view.findViewById(R.id.til_class);
		til_phone = view.findViewById(R.id.til_phone);
		til_email = view.findViewById(R.id.til_email);

		et_studentName = view.findViewById(R.id.et_studentName);
		et_phone = view.findViewById(R.id.et_phone);
		et_email = view.findViewById(R.id.et_email);
		sp_course = view.findViewById(R.id.sp_course);

		//todo: implements set on focus change listener
		et_studentName.setFocusChange(til_studentName);
		et_phone.setFocusChangeMobileNo(til_phone);
		et_email.setFocusChangeEmailId(til_email);
		sp_course.setFocusChange(til_class);

		//todo: implements textWatcher on fields
		et_studentName.addTextChangedListener(textWatcher);
		et_phone.addTextChangedListener(textWatcher);
		et_email.addTextChangedListener(textWatcher);
		sp_course.addTextChangedListener(textWatcher);

		TextView tvBack = view.findViewById(R.id.tvStudentBack);
		Button btnSubmit = view.findViewById(R.id.btn_submit);

		//todo: implement Se on click listener
		tvBack.setVisibility(View.VISIBLE);
		tvBack.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);

		ArrayAdapter<String> adapter = new ArrayAdapter<>(activity.getApplicationContext(),
				android.R.layout.simple_expandable_list_item_1, list);
		adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		sp_course.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch ( v.getId() ) {
			case R.id.tvStudentBack:
				dismiss();
				break;

			case R.id.btn_submit:
				isFlags = true;
				if ( checkMandatoryFields() ) {
					isFlags = false;
					dismiss();
				} else {
					isFlags = false;
					activity.toast(getString(R.string.check_mandatory));
				}
				break;
		}
	}

	private boolean checkMandatoryFields() {
		int count = 0;
		int totalCount = 4;

		data.setStudentName(et_studentName.toString());
		data.setStudentPhone(et_phone.toString());
		data.setStudentEmail(et_email.toString());
		data.setStudentCourseName(sp_course.toString());

		if ( !TextUtils.isEmpty(data.getStudentName()) ) {
			til_studentName.setDisabled();
			count++;
		}
		else {
			til_studentName.setErrorMessage();
		}

		if ( !TextUtils.isEmpty(data.getStudentPhone()) ) {
			til_phone.setDisabled();
			count++;
		}
		else {
			til_phone.setErrorMessage();
		}

		if ( !TextUtils.isEmpty(data.getStudentEmail()) ) {
			til_email.setDisabled();
			count++;
		}
		else {
			til_email.setErrorMessage();
		}

		if ( !TextUtils.isEmpty(data.getStudentCourseName()) ) {
			til_class.setDisabled();
			count++;
		}
		else {
			til_class.setErrorMessage();
		}
		return count != 0 && count == totalCount;
	}

	public class EditStudentData {
		private String studentName;
		private String studentPhone;
		private String studentEmail;
		private String studentCourseName;

		public String getStudentName() {
			return studentName;
		}

		public void setStudentName(String studentName) {
			this.studentName = studentName;
		}

		public String getStudentPhone() {
			return studentPhone;
		}

		public void setStudentPhone(String studentPhone) {
			this.studentPhone = studentPhone;
		}

		public String getStudentEmail() {
			return studentEmail;
		}

		public void setStudentEmail(String studentEmail) {
			this.studentEmail = studentEmail;
		}

		public String getStudentCourseName() {
			return studentCourseName;
		}

		public void setStudentCourseName(String studentCourseName) {
			this.studentCourseName = studentCourseName;
		}
	}

	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (isFlags) {
				checkMandatoryFields();
			}
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

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

}
