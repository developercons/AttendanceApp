package com.attendance.fragments;

import android.content.Context;
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
import com.attendance.adapters.CustomAdapter;
import com.attendance.custom_classes.CustomInputEditText;
import com.attendance.custom_classes.CustomSpinner;
import com.attendance.custom_classes.CustomTextInputLayout;
import com.attendance.database.MyDBHelper;
import com.attendance.helper_classes.ConstantsString;

public class AddStudentFragment extends Fragment implements View.OnClickListener {

    CustomTextInputLayout til_studentName, til_class, til_phone, til_email;
    CustomSpinner sp_class;
    CustomInputEditText et_studentName, et_phone, et_email;
    Button btn_submit;

    MyDBHelper dbHelper;

    String className, studentName, phone, email;
    private boolean flag = false;
	private AddDetailsActivity activity;

	public static AddStudentFragment newInstance() {
		return new AddStudentFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_add_student, container, false);
		dbHelper = MyDBHelper.getInstance(getActivity());
		initViews(view);

		btn_submit.setOnClickListener(this);
        textChangeListeners();
		CustomAdapter coursesAdapter = new CustomAdapter(activity, ConstantsString.coursesList);
        sp_class.setAdapter(coursesAdapter);
        return view;
    }

    private void textChangeListeners() {
        sp_class.addTextChangedListener(textWatcher);
        et_studentName.addTextChangedListener(textWatcher);
        et_phone.addTextChangedListener(textWatcher);
        et_email.addTextChangedListener(textWatcher);
    }

    private void initViews(View view) {
	    til_studentName = view.findViewById(R.id.til_studentName);
        til_class = view.findViewById(R.id.til_class);
        til_phone = view.findViewById(R.id.til_phone);
        til_email = view.findViewById(R.id.til_email);

        sp_class = view.findViewById(R.id.sp_course);

        et_studentName = view.findViewById(R.id.et_studentName);
        et_phone = view.findViewById(R.id.et_phone);
        et_email = view.findViewById(R.id.et_email);

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
            if(flag) {
                checkMandatoryFields();
            }
        }
    };

    public boolean checkMandatoryFields() {
        int totalCount = 4 , count = 0;
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

        if(!TextUtils.isEmpty(phone) && phone.length() == 10) {
            til_phone.setErrorEnabled(false);
            count++;
        } else {
            til_phone.setErrorMessage("Phone No. must be of 10 digit");
        }

        if(!TextUtils.isEmpty(email) && email.matches(ConstantsString.EMAIL_PATTERN)) {
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
                    AddStudentData data = new AddStudentData();
                    data.className = className;
                    data.email = email;
                    data.phone = phone;
                    data.studentName = studentName;
                    boolean inserted = dbHelper.insertStudentData(data);
                    if(inserted) {
                        flag = false;
                        setFieldsEmpty();
                        ConstantsString.showAlertDialog(getActivity(), "Data Inserted");
                    } else {
                        ConstantsString.showAlertDialog(getActivity(), "Email ID already exists");
                    }
                }
                break;
        }
    }

	public class AddStudentData {
		public String className;
		public String studentName;
		public String phone;
		public String email;
	}

    private void setFieldsEmpty() {
        sp_class.setText("");
        et_email.setText("");
        et_phone.setText("");
        et_studentName.setText("");
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
