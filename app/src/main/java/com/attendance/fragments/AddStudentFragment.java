package com.attendance.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.attendance.R;
import com.attendance.activities.AddDetailsActivity;
import com.attendance.custom_classes.CustomInputEditText;
import com.attendance.custom_classes.CustomSpinner;
import com.attendance.custom_classes.CustomTextInputLayout;
import com.attendance.helper_classes.Constants;

public class AddStudentFragment extends Fragment implements View.OnClickListener {

    CustomTextInputLayout til_studentName, til_class, til_phone, til_email;
    CustomSpinner sp_class;
    CustomInputEditText et_studentName, et_phone, et_email;
    Button btn_submit;

    String className, studentName, phone, email;
    private boolean flag = false;

    public static AddStudentFragment newInstance() {
		return new AddStudentFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_add_student, container, false);
		initViews(view);

		btn_submit.setOnClickListener(this);
        textChangeListeners();

        ArrayAdapter _adapterCourses = new ArrayAdapter(getActivity(), android.R.layout.activity_list_item,
                ((AddDetailsActivity) getActivity()).coursesList);
        sp_class.setAdapter(_adapterCourses);
        return view;
    }

    private void textChangeListeners() {
        sp_class.addTextChangedListener(textWatcher);
        et_studentName.addTextChangedListener(textWatcher);
        et_phone.addTextChangedListener(textWatcher);
        et_email.addTextChangedListener(textWatcher);
    }

    private void initViews(View view) {
	    til_studentName = (CustomTextInputLayout) view.findViewById(R.id.til_studentName);
        til_class = (CustomTextInputLayout) view.findViewById(R.id.til_class);
        til_phone = (CustomTextInputLayout) view.findViewById(R.id.til_phone);
        til_email = (CustomTextInputLayout) view.findViewById(R.id.til_email);

        sp_class = (CustomSpinner) view.findViewById(R.id.sp_course);

        et_studentName = (CustomInputEditText) view.findViewById(R.id.et_studentName);
        et_phone = (CustomInputEditText) view.findViewById(R.id.et_phone);
        et_email = (CustomInputEditText) view.findViewById(R.id.et_email);

        btn_submit = (Button) view.findViewById(R.id.btn_submit);
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

            if(flag) {
                checkMandatoryFields();
            }
        }
    };

    public boolean checkMandatoryFields() {
        int totalCount = 5 , count = 0;
        className = sp_class.getText().toString().trim();
        studentName = et_studentName.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
        email = et_email.getText().toString().trim();

        if(!TextUtils.isEmpty(className)) {
            til_class.setErrorEnabled(false);
            count++;
        } else {
            til_class.setErrorMessage();
        }

        if(!TextUtils.isEmpty(studentName)) {
            til_studentName.setErrorEnabled(false);
            count++;
        } else {
            til_studentName.setErrorMessage();
        }

        if(!TextUtils.isEmpty(phone)) {
            til_phone.setErrorEnabled(false);
            count++;
        } else {
            til_phone.setErrorMessage();
        }

        if(!TextUtils.isEmpty(email) && email.matches(Constants.EMAIL_PATTERN)) {
            til_email.setErrorEnabled(false);
            count++;
        } else {
            til_email.setErrorMessage("Invalid Email");
        }

        return count == totalCount;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                flag = true;
                if(checkMandatoryFields()) {

                }
                break;
        }
    }
}
