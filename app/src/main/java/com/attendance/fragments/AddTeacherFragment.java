package com.attendance.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.attendance.R;
import com.attendance.activities.AddDetailsActivity;
import com.attendance.adapters.CustomAdapter;
import com.attendance.custom_classes.CustomInputEditText;
import com.attendance.custom_classes.CustomSpinner;
import com.attendance.custom_classes.CustomTextInputLayout;
import com.attendance.database.MyDBHelper;
import com.attendance.helper_classes.PermissionUtils;
import com.attendance.helper_classes.ConstantsString;

public class AddTeacherFragment extends Fragment implements View.OnClickListener {
    CustomTextInputLayout til_teacherName, til_phone, til_email, til_password, til_qualification;
    CustomSpinner sp_qualification;
    CustomInputEditText et_teacherName, et_phone, et_email, et_password;
    Button btn_submit;
    boolean flag = false;

    MyDBHelper dbHelper;

    String teacherName, phone, email, password, qualification;
	private AddDetailsActivity activity;

    public static AddTeacherFragment newInstance() {
		return new AddTeacherFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_teacher, container, false);
		dbHelper = MyDBHelper.getInstance(getActivity());
		initViews(view);

		btn_submit.setOnClickListener(this);

		textChangeListeners();
		CustomAdapter adapter = new CustomAdapter(activity, ConstantsString.qualificationList);
        sp_qualification.setAdapter(adapter);

		return view;
	}

    private void textChangeListeners() {
        sp_qualification.addTextChangedListener(textWatcher);
        et_teacherName.addTextChangedListener(textWatcher);
        et_phone.addTextChangedListener(textWatcher);
        et_email.addTextChangedListener(textWatcher);
        et_password.addTextChangedListener(textWatcher);
    }

    private void initViews(View view) {
        til_teacherName = view.findViewById(R.id.til_teacherName);
        til_phone = view.findViewById(R.id.til_phone);
        til_email = view.findViewById(R.id.til_email);
        til_password = view.findViewById(R.id.til_password);
        til_qualification = view.findViewById(R.id.til_qualification);

        sp_qualification = view.findViewById(R.id.sp_qualification);

        et_teacherName = view.findViewById(R.id.et_teacherName);
        et_phone = view.findViewById(R.id.et_phone);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);

        btn_submit = view.findViewById(R.id.btn_submit);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
	        if ( flag ) {
		        checkMandatoryFields();
	        }
        }
    };

    public boolean checkMandatoryFields() {
        int totalCount = 5 , count = 0;
        teacherName = et_teacherName.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();
        qualification = sp_qualification.getText().toString().trim();

        if(!TextUtils.isEmpty(teacherName)) {
            til_teacherName.setErrorEnabled(false);
            count++;
        } else {
            til_teacherName.setErrorMessage();
        }

        if(!TextUtils.isEmpty(phone) && phone.length() == 10) {
            til_phone.setErrorEnabled(false);
            count++;
        } else {
            til_phone.setErrorMessage("Phone No. must be of 10 digits");
        }

        if(!TextUtils.isEmpty(email) && email.matches(ConstantsString.EMAIL_PATTERN)) {
            til_email.setErrorEnabled(false);
            count++;
        } else {
            til_email.setErrorMessage("Invalid Email");
        }

        if(!TextUtils.isEmpty(password) && password.length() > 4) {
            til_password.setErrorEnabled(false);
            count++;
        } else {
            til_password.setErrorMessage("Password Length should be greater than 4");
        }

        if(!TextUtils.isEmpty(qualification)) {
            til_qualification.setErrorEnabled(false);
            count++;
        } else {
            til_qualification.setErrorMessage();
        }
        return count == totalCount;
    }

    @Override
    public void onClick(View v) {
	    switch (v.getId()) {
            case R.id.btn_submit:
                flag = true;
                if (checkMandatoryFields()) {
                    AddTeacherData data = new AddTeacherData();
                    data.teacherName = teacherName;
                    data.phone = phone;
                    data.email = email;
                    data.password = password;
                    data.qualification = qualification;
                    boolean inserted = dbHelper.insertTeacherData(data);
                    if(inserted && PermissionUtils.isGranted()) {
                        flag = false;
                        setFieldsEmpty();
                        ConstantsString.showAlertDialog(getActivity(), "Data Inserted");

                        SmsManager sms = SmsManager.getDefault();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Email Id: ").append(email).append(" and Password: ").append(password);
                        sms.sendTextMessage(phone, null, stringBuilder.toString(), null, null);
                        ConstantsString.showAlertDialog(getActivity(), "Data Inserted");
                    } else {
                        ConstantsString.showAlertDialog(getActivity(), "Data Insertion Failed");
                        PermissionUtils.secondTimePermission(activity, activity.permissionList, activity.PERMISSION_REQUEST_CODE);
                        ConstantsString.showAlertDialog(getActivity(), "Data Insertion Failed");
                    }
                }
                break;
        }
    }

    private void setFieldsEmpty() {
        et_teacherName.setText("");
        et_email.setText("");
        et_phone.setText("");
        et_password.setText("");
        sp_qualification.setText("");
    }

	public class AddTeacherData {
		public String teacherName;
		public String phone;
		public String email;
		public String password;
		public String qualification;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if ( context instanceof AddDetailsActivity ) {
			activity = (AddDetailsActivity)context;
		} else {
			throw new ClassCastException("Wrong activity is type cast");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		activity = null;
	}
}
