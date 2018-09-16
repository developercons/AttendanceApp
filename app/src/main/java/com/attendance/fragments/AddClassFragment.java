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
import com.attendance.custom_classes.CustomAutoCompleteTextView;
import com.attendance.custom_classes.CustomInputEditText;
import com.attendance.custom_classes.CustomSpinner;
import com.attendance.custom_classes.CustomTextInputLayout;
import com.attendance.helper_classes.Constants;

public class AddClassFragment extends Fragment implements View.OnClickListener {

	CustomTextInputLayout til_courseName, til_semester, til_teacherEmail, til_teacherName;
	CustomInputEditText et_teacherName;
	CustomSpinner sp_courseName, sp_semester;
	CustomAutoCompleteTextView ac_teacherEmail;

	String courseName, semester, teacherEmail, teacherName;
	Button btn_submit;

	boolean flag = false;

	public static AddClassFragment newInstance() {
		return new AddClassFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_class, container, false);

		initViews(view);

		btn_submit.setOnClickListener(this);

        textChangeListeners();

        ArrayAdapter _adapterCourses = new ArrayAdapter(getActivity(), android.R.layout.activity_list_item,
                ((AddDetailsActivity) getActivity()).coursesList);
        ArrayAdapter _adapterSemester = new ArrayAdapter(getActivity(), android.R.layout.activity_list_item,
                ((AddDetailsActivity) getActivity()).semesterList);

        //set Adapters
        sp_courseName.setAdapter(_adapterCourses);
        sp_semester.setAdapter(_adapterSemester);
		return view;
	}

    private void textChangeListeners() {
	    et_teacherName.addTextChangedListener(textWatcher);
        sp_courseName.addTextChangedListener(textWatcher);
        sp_semester.addTextChangedListener(textWatcher);
        ac_teacherEmail.addTextChangedListener(textWatcher);
    }

    private void initViews(View view) {
	    til_courseName = (CustomTextInputLayout) view.findViewById(R.id.til_courseName);
        til_semester = (CustomTextInputLayout) view.findViewById(R.id.til_semester);
        til_teacherEmail = (CustomTextInputLayout) view.findViewById(R.id.til_teacherEmail);
        til_teacherName = (CustomTextInputLayout) view.findViewById(R.id.til_teacherName);

        et_teacherName = (CustomInputEditText) view.findViewById(R.id.et_teacherName);

        sp_courseName = (CustomSpinner) view.findViewById(R.id.sp_courseName);
        sp_semester = (CustomSpinner) view.findViewById(R.id.sp_semester);

        ac_teacherEmail = (CustomAutoCompleteTextView) view.findViewById(R.id.ac_teacherEmail);

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
        teacherName = et_teacherName.getText().toString().trim();
        courseName = sp_courseName.getText().toString().trim();
        semester = sp_semester.getText().toString().trim();
        teacherEmail = ac_teacherEmail.getText().toString().trim();

        if(!TextUtils.isEmpty(teacherName)) {
            til_teacherName.setErrorEnabled(false);
            count++;
        } else {
            til_teacherName.setErrorMessage();
        }

        if(!TextUtils.isEmpty(courseName)) {
            til_courseName.setErrorEnabled(false);
            count++;
        } else {
            til_courseName.setErrorMessage();
        }

        if(!TextUtils.isEmpty(semester)) {
            til_semester.setErrorEnabled(false);
            count++;
        } else {
            til_semester.setErrorMessage();
        }

        if(!TextUtils.isEmpty(teacherEmail) && teacherEmail.matches(Constants.EMAIL_PATTERN)) {
            til_teacherEmail.setErrorEnabled(false);
            count++;
        } else {
            til_teacherEmail.setErrorMessage("Invalid Email");
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
