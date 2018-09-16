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

public class TeacherDialogFragment extends DialogFragment implements View.OnClickListener {
	public static final String TAG = "TEACHER_DETAILS_UPDATE";
	private ViewDetailsActivity activity;
	private ViewGroup parent;
	private CustomTextInputLayout til_teacherName, til_phone, til_email, til_password, til_qualification;
	private CustomInputEditText et_teacherName, et_phone, et_email, et_password;
	private CustomSpinner sp_qualification;
	private EditTeacherData data;
	private boolean isFlag = false;
	ArrayList<String> list = new ArrayList<>();

	public TeacherDialogFragment newInstance(ViewGroup parent) {
		this.parent = parent;
		return new TeacherDialogFragment();
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.fragment_add_teacher, parent, false);
		data = new EditTeacherData();
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

		til_teacherName = view.findViewById(R.id.til_teacherName);
		til_phone = view.findViewById(R.id.til_phone);
		til_email = view.findViewById(R.id.til_email);
		til_password = view.findViewById(R.id.til_password);
		til_qualification = view.findViewById(R.id.til_qualification);

		et_teacherName = view.findViewById(R.id.et_teacherName);
		et_phone = view.findViewById(R.id.et_phone);
		et_email = view.findViewById(R.id.et_email);
		et_password = view.findViewById(R.id.et_password);
		sp_qualification = view.findViewById(R.id.sp_qualification);

		TextView tvBack = view.findViewById(R.id.tvTeacherBack);
		Button btnSubmit = view.findViewById(R.id.btn_submit);

		//TODO: implements set on Focus change listener
		et_teacherName.setFocusChange(til_teacherName);
		et_phone.setFocusChangeMobileNo(til_phone);
		et_email.setFocusChangeEmailId(til_email);
		et_password.setFocusChange(til_password);
		sp_qualification.setFocusChange(til_qualification);


		//TODO: implements textWatcher on fields
		et_teacherName.addTextChangedListener(textWatcher);
		et_phone.addTextChangedListener(textWatcher);
		et_email.addTextChangedListener(textWatcher);
		et_password.addTextChangedListener(textWatcher);
		sp_qualification.addTextChangedListener(textWatcher);

		// TODO: implement click listener
		tvBack.setVisibility(View.VISIBLE);
		tvBack.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);

		ArrayAdapter<String> adapter = new ArrayAdapter<>(activity.getApplicationContext(),
				android.R.layout.simple_expandable_list_item_1, list);
		adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		sp_qualification.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch ( v.getId() ) {
			case R.id.tvTeacherBack:
				dismiss();
				break;

			case R.id.btn_submit:
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
		int totalCount = 5;
		int count = 0;

		data.setTeacherName(et_teacherName.toString());
		data.setPhone(et_phone.toString());
		data.setEmail(et_email.toString());
		data.setPassword(et_password.toString());
		data.setQualification(sp_qualification.toString());

		if ( !TextUtils.isEmpty(data.getTeacherName()) ) {
			til_teacherName.setDisabled();
			count++;
		} else {
			til_teacherName.setErrorMessage();
		}

		if ( !TextUtils.isEmpty(data.getPhone()) ) {
			til_phone.setDisabled();
			count++;
		} else {
			til_phone.setErrorMessage();
		}

		if ( !TextUtils.isEmpty(data.getEmail()) ) {
			til_email.setDisabled();
			count++;
		} else {
			til_email.setErrorMessage();
		}

		if ( !TextUtils.isEmpty(data.getPassword()) ) {
			til_password.setDisabled();
			count++;
		} else {
			til_password.setErrorMessage();
		}

		if ( !TextUtils.isEmpty(data.getQualification()) ) {
			til_qualification.setDisabled();
			count++;
		} else {
			til_qualification.setErrorMessage();
		}
		return count != 0 && count == totalCount;
	}

	public class EditTeacherData {
		private String teacherName;
		private String phone;
		private String email;
		private String password;
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

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getQualification() {
			return qualification;
		}

		public void setQualification(String qualification) {
			this.qualification = qualification;
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
