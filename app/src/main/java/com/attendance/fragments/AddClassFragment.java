package com.attendance.fragments;

import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.attendance.R;
import com.attendance.activities.AddDetailsActivity;
import com.attendance.adapters.CustomAdapter;
import com.attendance.custom_classes.CustomAutoCompleteTextView;
import com.attendance.custom_classes.CustomInputEditText;
import com.attendance.custom_classes.CustomSpinner;
import com.attendance.custom_classes.CustomTextInputLayout;
import com.attendance.data_models.Teacher;
import com.attendance.database.MyDBHelper;
import com.attendance.helper_classes.ConstantsString;

import java.util.ArrayList;

public class AddClassFragment extends Fragment implements View.OnClickListener {

	CustomTextInputLayout til_courseName, til_semester, til_teacherEmail, til_teacherName;
	CustomInputEditText et_teacherName;
	CustomSpinner sp_courseName, sp_semester;
	CustomAutoCompleteTextView ac_teacherEmail;

	String courseName, semester, teacherEmail, teacherName;
	Button btn_submit;
    ArrayList<Teacher> teacherAllDataList = new ArrayList<>();
    ArrayList<String> teacherEmailList = new ArrayList<>();

	MyDBHelper dbHelper;

	boolean flag = false;
	private AddDetailsActivity activity;

	public static AddClassFragment newInstance() {
		return new AddClassFragment();
	}

    public class AddClassData {
        public String courseName;
        public String semester;
        public String teacherEmail;
        public String teacherName;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            teacherEmailList.clear();
            teacherAllDataList.clear();
            teacherAllDataList.addAll(MyDBHelper.getInstance(getActivity()).getTeacherEmail());
            for(Teacher teacher : MyDBHelper.getInstance(getActivity()).getTeacherEmail()) {
                teacherEmailList.add(teacher.email);
            }
            CustomAdapter _adapterEmail = new CustomAdapter(activity, ac_teacherEmail, teacherEmailList);
            ac_teacherEmail.setAdapter(null);
            ac_teacherEmail.setAdapter(_adapterEmail);
        }
    }

    @Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_class, container, false);
		dbHelper = MyDBHelper.getInstance(getActivity());

		initViews(view);

		btn_submit.setOnClickListener(this);

        textChangeListeners();
		CustomAdapter coursesAdapter = new CustomAdapter(activity, sp_courseName, ConstantsString.coursesList);
		CustomAdapter semesterAdapter = new CustomAdapter(activity, sp_courseName, ConstantsString.semesterList);

        //set Adapters
        sp_courseName.setAdapter(coursesAdapter);
        sp_semester.setAdapter(semesterAdapter);

        ac_teacherEmail.setOnItemClickListener((parent, view1, position, id) -> {
            String email = ac_teacherEmail.getText().toString().trim();
            String name = teacherAllDataList.get(teacherEmailList.indexOf(email)).name;
            et_teacherName.setText(name);
            et_teacherName.setTextColor(Color.BLACK);
        });
		//set Adapters
        sp_courseName.setAdapter(coursesAdapter);
        sp_semester.setAdapter(semesterAdapter);
		return view;
	}

    private void textChangeListeners() {
	    et_teacherName.addTextChangedListener(textWatcher);
        sp_courseName.addTextChangedListener(textWatcher);
        sp_semester.addTextChangedListener(textWatcher);
        ac_teacherEmail.addTextChangedListener(textWatcher);
    }

    private void initViews(View view) {
	    til_courseName = view.findViewById(R.id.til_courseName);
        til_semester = view.findViewById(R.id.til_semester);
        til_teacherEmail = view.findViewById(R.id.til_teacherEmail);
        til_teacherName = view.findViewById(R.id.til_teacherName);

        et_teacherName = view.findViewById(R.id.et_teacherName);

        sp_courseName = view.findViewById(R.id.sp_courseName);
        sp_semester = view.findViewById(R.id.sp_semester);

        ac_teacherEmail = view.findViewById(R.id.ac_teacherEmail);

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

        if(!TextUtils.isEmpty(teacherEmail) && teacherEmail.matches(ConstantsString.EMAIL_PATTERN)) {
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
                    AddClassData data = new AddClassData();
                    data.courseName = courseName;
                    data.semester = semester;
                    data.teacherEmail = teacherEmail;
                    data.teacherName = teacherName;
                    boolean inserted = dbHelper.insertClassData(data);
                    if(inserted) {
                    	flag = false;
                        setFieldsEmpty();
                        ConstantsString.showAlertDialog(getActivity(), "Data Inserted");
                    } else {
                        ConstantsString.showAlertDialog(getActivity(), "Data Insertion Failed");
                    }
                }
                break;
        }
    }

    private void setFieldsEmpty() {
        sp_courseName.setText("");
        sp_semester.setText("");
        ac_teacherEmail.setText("");
        et_teacherName.setText("");
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
