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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.attendance.R;
import com.attendance.activities.ViewDetailsActivity;
import com.attendance.adapters.CustomAdapter;
import com.attendance.adapters.EditStudentDetailsAdapter;
import com.attendance.custom_classes.CustomInputEditText;
import com.attendance.custom_classes.CustomSpinner;
import com.attendance.custom_classes.CustomTextInputLayout;
import com.attendance.database.MyDBHelper;
import com.attendance.helper_classes.ConstantsString;

public class StudentDialogFragment extends DialogFragment implements View.OnClickListener {

	public static final String TAG = "STUDENT_DETAILS_UPDATE";
	private ViewDetailsActivity activity;
	private ViewGroup parent;
	private CustomTextInputLayout til_studentName, til_class, til_phone;
	private CustomInputEditText et_studentName, et_phone;
	private CustomSpinner sp_course;
	private boolean isFlags = false;
	private EditStudentData studentData;
	private MyDBHelper dbHelper;
	private StudentRowData rowData;

	public StudentDialogFragment newInstance(ViewGroup parent) {
		this.parent = parent;
		return new StudentDialogFragment();
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.student_dialog, parent, false);
		studentData = new EditStudentData();
		dbHelper = MyDBHelper.getInstance(activity);
		setupView(view);
		builder.setView(view);
		return builder.create();
	}

	private void setupView(View view) {
		til_studentName = view.findViewById(R.id.til_editStudentName);
		til_class = view.findViewById(R.id.til_editClass);
		til_phone = view.findViewById(R.id.til_editPhone);

		et_studentName = view.findViewById(R.id.et_editStudentName);
		et_phone = view.findViewById(R.id.et_editPhone);
		sp_course = view.findViewById(R.id.sp_editCourse);

		//todo: implements set on focus change listener
		et_studentName.setFocusChange(til_studentName);
		et_phone.setFocusChangeMobileNo(til_phone);
		sp_course.setFocusChange(til_class);

		//todo: implements textWatcher on fields
		et_studentName.addTextChangedListener(textWatcher);
		et_phone.addTextChangedListener(textWatcher);
		sp_course.addTextChangedListener(textWatcher);

		TextView tvBack = view.findViewById(R.id.tvStudentBack);
		Button btnSubmit = view.findViewById(R.id.btn_editSubmit);

		//todo: implement Se on click listener
		tvBack.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);

		CustomAdapter adapter = new CustomAdapter(activity, sp_course, ConstantsString.coursesList);
		sp_course.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch ( v.getId() ) {
			case R.id.tvStudentBack:
				dismiss();
				break;

			case R.id.btn_editSubmit:
				isFlags = true;
				if ( checkMandatoryFields() ) {
					isFlags = false;
					if (dbHelper.updateStudentData(studentData)) {
						StudentDetailsFragment fragment = (StudentDetailsFragment ) getTargetFragment();
						if (fragment != null){
							fragment.updateAdapter();
							dismiss();
						}
					} else {
						activity.toast("Database is not update");
					}
				} else {
					isFlags = false;
					activity.toast(getString(R.string.check_mandatory));
				}
				break;
		}
	}

	private boolean checkMandatoryFields() {
		int count = 0;
		int totalCount = 3;

		studentData.setStudentName(et_studentName.toString());
		studentData.setStudentPhone(et_phone.toString());
		studentData.setStudentCourseName(sp_course.toString());
		studentData.setStudentData(rowData);

		if ( !TextUtils.isEmpty(studentData.getStudentName()) ) {
			til_studentName.setErrorDisabled();
			count++;
		}
		else {
			til_studentName.setErrorMessage();
		}

		if ( !TextUtils.isEmpty(studentData.getStudentPhone()) ) {
			til_phone.setErrorDisabled();
			count++;
		}
		else {
			til_phone.setErrorMessage();
		}

		if ( !TextUtils.isEmpty(studentData.getStudentCourseName()) ) {
			til_class.setErrorDisabled();
			count++;
		}
		else {
			til_class.setErrorMessage();
		}
		return count != 0 && count == totalCount;
	}

	public void rowData(EditStudentDetailsAdapter.RowStudentData data) {
		rowData = new StudentRowData();
		rowData.setStudentName(data.getStudentName());
		rowData.setClassName(data.getClassName());
		rowData.setStudentEmailId(data.getStudentEmailId());
	}

	public class EditStudentData {
		private String studentName;
		private String studentPhone;
		private String studentCourseName;
		private StudentRowData data;

		public String getStudentName() {
			return studentName;
		}

		void setStudentName(String studentName) {
			this.studentName = studentName;
		}

		public String getStudentPhone() {
			return studentPhone;
		}

		void setStudentPhone(String studentPhone) {
			this.studentPhone = studentPhone;
		}

		public String getStudentCourseName() {
			return studentCourseName;
		}

		void setStudentCourseName(String studentCourseName) {
			this.studentCourseName = studentCourseName;
		}

		public StudentRowData getStudentData() {
			return data;
		}

		void setStudentData(StudentRowData data) {
			this.data = data;
		}
	}

	public class StudentRowData {
		private String studentName;
		private String className;
		private String studentEmailId;

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

		public String getStudentEmailId() {
			return studentEmailId;
		}

		public void setStudentEmailId(String studentEmailId) {
			this.studentEmailId = studentEmailId;
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
	public void onResume() {
		super.onResume();
		Window window = getDialog().getWindow();
		if ( window != null ) {
			window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
			window.setGravity(Gravity.CENTER);
		}
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

}
