package com.attendance.fragments;

import android.content.Intent;
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
import com.attendance.activities.CourseListActivity;
import com.attendance.adapters.CustomAdapter;
import com.attendance.custom_classes.CustomAutoCompleteTextView;
import com.attendance.custom_classes.CustomInputEditText;
import com.attendance.custom_classes.CustomTextInputLayout;
import com.attendance.data_models.Teacher;
import com.attendance.database.MyDBHelper;
import com.attendance.helper_classes.ConstantsString;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherLoginFragment extends Fragment implements View.OnClickListener {

    CustomTextInputLayout til_teacherEmail, til_password;
    CustomInputEditText et_password;
    CustomAutoCompleteTextView ac_teacherEmail;
    ArrayList<Teacher> teacherAllDataList = new ArrayList<>();
    ArrayList<String> teacherEmailList = new ArrayList<>();
    MyDBHelper dbHelper;

    String email, password;

    Button btn_submit;
    boolean flag = false;

    public static TeacherLoginFragment newInstance() {
        return new TeacherLoginFragment();
    }

    public TeacherLoginFragment() {
        // Required empty public constructor
    }

    public class TeacherLoginData {
        public String _email;
        public String _password;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            teacherAllDataList.addAll(MyDBHelper.getInstance(getActivity()).getTeacherEmail());
            for(Teacher teacher : MyDBHelper.getInstance(getActivity()).getTeacherEmail()) {
                teacherEmailList.add(teacher.email);
            }
	        CustomAdapter adapter = new CustomAdapter(getActivity(), teacherEmailList);
            ac_teacherEmail.setAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher_login, container, false);
        dbHelper = MyDBHelper.getInstance(getActivity());
        initViews(view);

        btn_submit.setOnClickListener(this);
        textChangeListeners();
        return view;
    }

    private void textChangeListeners() {
        ac_teacherEmail.addTextChangedListener(textWatcher);
        et_password.addTextChangedListener(textWatcher);
    }

    private void initViews(View view) {
        til_teacherEmail = (CustomTextInputLayout) view.findViewById(R.id.til_teacherEmail);
        til_password = (CustomTextInputLayout) view.findViewById(R.id.til_password);

        et_password = (CustomInputEditText) view.findViewById(R.id.et_password);

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
        int totalCount = 2 , count = 0;
        email = ac_teacherEmail.getText().toString().trim();
        password = et_password.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && email.matches(ConstantsString.EMAIL_PATTERN)) {
            til_teacherEmail.setErrorEnabled(false);
            count++;
        } else {
            til_teacherEmail.setErrorMessage("Invalid Email");
        }

        if(!TextUtils.isEmpty(password) && password.length() > 4) {
            til_password.setErrorEnabled(false);
            count++;
        } else {
            til_password.setErrorMessage("Password Length should be greater than 4");
        }
        return count == totalCount;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                flag = true;
                if(checkMandatoryFields()) {
                    TeacherLoginData data = new TeacherLoginData();
                    data._email = email;
                    data._password = password;
                    boolean inserted = dbHelper.checkTeacherEmail(data);
                    if(inserted) {
                        Intent intent = new Intent(getActivity(), CourseListActivity.class);
                        intent.putExtra("TEACHER_EMAIL", email);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        ConstantsString.showAlertDialog(getActivity(), "Teacher does not exits.Please " +
		                        "Enter Valid Email Address");
                    }
                }
                break;
        }
    }
}
