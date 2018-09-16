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
import com.attendance.adapters.EditClassDetailsAdapter;
import com.attendance.custom_classes.CustomAutoCompleteTextView;
import com.attendance.custom_classes.CustomInputEditText;
import com.attendance.custom_classes.CustomSpinner;
import com.attendance.custom_classes.CustomTextInputLayout;
import com.attendance.database.MyDBHelper;
import com.attendance.helper_classes.ConstantsString;


public class ClassDialogFragment extends DialogFragment implements View.OnClickListener {

	public static final String TAG = "CLASS_DETAILS_UPDATE";
	private ViewDetailsActivity activity;
	private ViewGroup parent;
	private CustomTextInputLayout til_courseName,til_semester, til_teacherEmail, til_teacherName;
	private CustomSpinner sp_courseName,sp_semester;
	private CustomAutoCompleteTextView ac_teacherEmail;
	private CustomInputEditText et_teacherName;
	private EditClassData data;
	private RowData rowData;
	private MyDBHelper dbHelper;
	private boolean isFlag = false;

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
		dbHelper = MyDBHelper.getInstance(activity);
		setupView(view);
		builder.setView(view);
		return builder.create();
	}

	private void setupView(View view) {
		til_courseName = view.findViewById(R.id.til_courseName);
		til_semester = view.findViewById(R.id.til_semester);
		til_teacherEmail = view.findViewById(R.id.til_teacherEmail);
		til_teacherName = view.findViewById(R.id.til_teacherName);
		sp_courseName = view.findViewById(R.id.sp_courseName);
		sp_semester = view.findViewById(R.id.sp_semester);
		ac_teacherEmail = view.findViewById(R.id.ac_teacherEmail);
		et_teacherName = view.findViewById(R.id.et_teacherName);
		TextView tvBack = view.findViewById(R.id.tvClassBack);
		Button btnSubmit = view.findViewById(R.id.btn_submit);
		
		// TODO: set focus change listener
		sp_courseName.setFocusChange(til_courseName);
		sp_semester.setFocusChange(til_semester);
		ac_teacherEmail.setFocusChangeEmailId(til_teacherEmail);
		et_teacherName.setFocusChange(til_teacherName);

		//TODO: implements textWatcher on Fields
		sp_courseName.addTextChangedListener(textWatcher);
		sp_semester.addTextChangedListener(textWatcher);
		ac_teacherEmail.addTextChangedListener(textWatcher);
		et_teacherName.addTextChangedListener(textWatcher);

		//todo: implement click listener
		tvBack.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
		tvBack.setVisibility(View.VISIBLE);

		CustomAdapter coursesAdapter = new CustomAdapter
				(activity, sp_courseName, ConstantsString.coursesList);
		CustomAdapter semesterAdapter = new CustomAdapter
						(activity, sp_semester, ConstantsString.semesterList);
		sp_courseName.setAdapter(coursesAdapter);
		sp_semester.setAdapter(semesterAdapter);

	}

	@Override
	public void onClick(View v) {
		switch ( v.getId() ) {
			case R.id.tvClassBack:
				dismiss();
				break;

			case R.id.btn_submit:
				isFlag = true;
				if ( checkMandatoryFields() ) {
					isFlag = false;
					if ( dbHelper.updateClassData(data) ) {
						dismiss();
					}
					else {
						activity.toast("Database is not update");
					}
				}
				else {
					isFlag = false;
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
		data.setRowData(rowData);

		if (!TextUtils.isEmpty(data.getCourseName())) {
			til_courseName.setDisabled();
			count++;
		} else {
			til_courseName.setErrorMessage();
		}

		if (!TextUtils.isEmpty(data.getSemester())) {
			til_semester.setDisabled();
			count++;
		} else {
			til_semester.setErrorMessage();
		}

		if (!TextUtils.isEmpty(data.getTeacherEmailId())) {
			til_teacherEmail.setDisabled();
			count++;
		} else {
			til_teacherEmail.setErrorMessage();
		}

		if (!TextUtils.isEmpty(data.getTeacherName())) {
			til_teacherName.setDisabled();
			count++;
		} else {
			til_teacherName.setErrorMessage();
		}
		return count != 0 && count == totalCount;
	}

	public void rowData(EditClassDetailsAdapter.RowData data) {
		rowData = new RowData();
		rowData.setCourseName(data.getCourseName());
		rowData.setSemester(data.getSemester());
//		activity.toast(data.getPosition());
	}

	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if ( isFlag ) {
				checkMandatoryFields();
			}
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	public class RowData {
		private String courseName;
		private String semester;

		public String getCourseName() {
			return courseName;
		}

		public String getSemester() {
			return semester;
		}

		void setCourseName(String courseName) {
			this.courseName = courseName;
		}

		void setSemester(String semester) {
			this.semester = semester;
		}
	}

	public class EditClassData {
		private RowData rowData;
		private String courseName;
		private String semester;
		private String teacherEmailId;
		private String teacherName;

		public RowData getRowData() {
			return rowData;
		}

		public void setRowData(RowData rowData) {
			this.rowData = rowData;
		}

		public String getCourseName() {
			return courseName;
		}

		void setCourseName(String courseName) {
			this.courseName = courseName;
		}

		public String getSemester() {
			return semester;
		}

		void setSemester(String semester) {
			this.semester = semester;
		}

		public String getTeacherEmailId() {
			return teacherEmailId;
		}

		void setTeacherEmailId(String teacherEmailId) {
			this.teacherEmailId = teacherEmailId;
		}

		public String getTeacherName() {
			return teacherName;
		}

		void setTeacherName(String teacherName) {
			this.teacherName = teacherName;
		}
	}

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
