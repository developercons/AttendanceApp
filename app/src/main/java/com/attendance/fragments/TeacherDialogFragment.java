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
import com.attendance.custom_classes.CustomInputEditText;
import com.attendance.custom_classes.CustomSpinner;
import com.attendance.custom_classes.CustomTextInputLayout;
import com.attendance.helper_classes.ConstantsString;

public class TeacherDialogFragment extends DialogFragment implements View.OnClickListener {
	public static final String TAG = "TEACHER_DETAILS_UPDATE";
	private ViewDetailsActivity activity;
	private ViewGroup parent;
	private CustomTextInputLayout til_teacherName, til_phone, til_qualification;
	private CustomInputEditText et_teacherName, et_phone;
	private CustomSpinner sp_qualification;
	private EditTeacherData data;
	private boolean isFlag = false;

	public TeacherDialogFragment newInstance(ViewGroup parent) {
		this.parent = parent;
		return new TeacherDialogFragment();
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.teacher_dialog, parent, false);
		data = new EditTeacherData();
		setupView(view);
		builder.setView(view);
		return builder.create();
	}

	private void setupView(View view) {
		til_teacherName = view.findViewById(R.id.til_editTeacherName);
		til_phone = view.findViewById(R.id.til_editPhone);
		til_qualification = view.findViewById(R.id.til_editQualification);

		et_teacherName = view.findViewById(R.id.et_editTeacherName);
		et_phone = view.findViewById(R.id.et_editPhone);
		sp_qualification = view.findViewById(R.id.sp_editQualification);

		TextView tvBack = view.findViewById(R.id.tvTeacherBack);
		Button btnSubmit = view.findViewById(R.id.btn_editSubmit);

		//TODO: implements set on Focus change listener
		et_teacherName.setFocusChange(til_teacherName);
		et_phone.setFocusChangeMobileNo(til_phone);
		sp_qualification.setFocusChange(til_qualification);


		//TODO: implements textWatcher on fields
		et_teacherName.addTextChangedListener(textWatcher);
		et_phone.addTextChangedListener(textWatcher);
		sp_qualification.addTextChangedListener(textWatcher);

		// TODO: implement click listener
		tvBack.setVisibility(View.VISIBLE);
		tvBack.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);

		CustomAdapter adapter = new CustomAdapter
				(activity, sp_qualification, ConstantsString.qualificationList);
		sp_qualification.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch ( v.getId() ) {
			case R.id.tvTeacherBack:
				dismiss();
				break;

			case R.id.btn_editSubmit:
				isFlag = true;
				if (checkMandatoryFields()) {
					isFlag = false;
					dismiss();
				} else {
					isFlag = false;
					activity.toast(getString(R.string.check_mandatory));
				}
				break;

		}
	}

	private boolean checkMandatoryFields() {
		int totalCount = 3;
		int count = 0;

		data.setTeacherName(et_teacherName.toString());
		data.setPhone(et_phone.toString());
		data.setQualification(sp_qualification.toString());

		if ( !TextUtils.isEmpty(data.getTeacherName()) ) {
			til_teacherName.setErrorDisabled();
			count++;
		} else {
			til_teacherName.setErrorMessage();
		}

		if ( !TextUtils.isEmpty(data.getPhone()) ) {
			til_phone.setErrorDisabled();
			count++;
		} else {
			til_phone.setErrorMessage();
		}

		if ( !TextUtils.isEmpty(data.getQualification()) ) {
			til_qualification.setErrorDisabled();
			count++;
		} else {
			til_qualification.setErrorMessage();
		}
		return count != 0 && count == totalCount;
	}

	public class EditTeacherData {
		private String teacherName;
		private String phone;
		private String qualification;

		public String getTeacherName() {
			return teacherName;
		}

		public void setTeacherName(String teacherName) {
			this.teacherName = teacherName;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getQualification() {
			return qualification;
		}

		public void setQualification(String qualification) {
			this.qualification = qualification;
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

	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (isFlag) {
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
