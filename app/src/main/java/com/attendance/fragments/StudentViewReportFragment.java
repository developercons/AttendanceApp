package com.attendance.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.attendance.R;
import com.attendance.activities.ViewReportsActivity;
import com.attendance.adapters.CustomAdapter;
import com.attendance.custom_classes.CustomAutoCompleteTextView;
import com.attendance.custom_classes.CustomSpinner;
import com.attendance.custom_classes.CustomTextInputLayout;
import com.attendance.database.MyDBHelper;
import com.jjoe64.graphview.GraphView;

import java.util.ArrayList;
import java.util.Calendar;

public class StudentViewReportFragment extends Fragment implements View.OnClickListener {
	private CustomTextInputLayout til_studentEmailId, til_month, til_year;
	private CustomAutoCompleteTextView ac_studentEmailId;
	private CustomSpinner sp_month, sp_year;
	private LinearLayout llStatus;
	private GraphView graph;
	private ArrayList<String> monthList = new ArrayList<>();
	private ArrayList<String> yearList = new ArrayList<>();
	private ViewReportsActivity activity;
	private boolean isFlag = false;
	private StudentReport report;
	private MyDBHelper dbHelper;

	public static StudentViewReportFragment newInstance() {
		return new StudentViewReportFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_student_view_report, container, false);
		report = new StudentReport();
		dbHelper = MyDBHelper.getInstance(activity);
		setupView(view);
		return view;
	}

	private void setupView(View view) {
		llStatus = view.findViewById(R.id.ll_status);
		graph = view.findViewById(R.id.graph);
		til_studentEmailId = view.findViewById(R.id.til_reportStudentEmailId);
		til_month = view.findViewById(R.id.til_month);
		til_year = view.findViewById(R.id.til_year);

		ac_studentEmailId = view.findViewById(R.id.ac_reportStudentEmailId);
		sp_month = view.findViewById(R.id.sp_month);
		sp_year = view.findViewById(R.id.sp_year);

		//todo: implement setOnFocusChangeListener
		ac_studentEmailId.setFocusChangeEmailId(til_studentEmailId);
		sp_month.setFocusChange(til_month);
		sp_year.setFocusChange(til_year);

		//todo: implement textWatcher on fields
		ac_studentEmailId.addTextChangedListener(textWatcher);
		sp_month.addTextChangedListener(textWatcher);
		sp_year.addTextChangedListener(textWatcher);

		//todo: implement setOnClickListener
		view.findViewById(R.id.btn_report).setOnClickListener(this);

		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		for ( int i = 1; i <= 12; i++ ) {
			monthList.add(String.valueOf(i));
		}

		for ( int i = 2000; i <= currentYear; i++ ) {
			yearList.add(String.valueOf(i));
		}

		CustomAdapter emailIdAdapter = new CustomAdapter(activity, dbHelper.getStudentEmailId());
		CustomAdapter monthAdapter = new CustomAdapter(activity, monthList);
		CustomAdapter yearAdapter = new CustomAdapter(activity, yearList);
		ac_studentEmailId.setAdapter(emailIdAdapter);
		sp_month.setAdapter(monthAdapter);
		sp_year.setAdapter(yearAdapter);

		ac_studentEmailId.setOnClickListener(v -> ac_studentEmailId.showDropDown());
	}

	private boolean checkMandatoryFields() {
		report.setStudentEmailId(ac_studentEmailId.toString());
		report.setMonth(sp_month.toString());
		report.setYear(sp_year.toString());

		if ( ac_studentEmailId.toString().matches(ac_studentEmailId.emailPattern) ) {
			til_studentEmailId.setErrorDisabled();
		} else{
			til_studentEmailId.setErrorMessage("Please enter valid student email id");
		}
		sp_month.isFieldEmpty(til_month);
		sp_year.isFieldEmpty(til_year);

		return ac_studentEmailId.toString().matches(ac_studentEmailId.emailPattern) &&
				ac_studentEmailId.isEmpty() && sp_month.isEmpty() && sp_year.isEmpty();
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

	@Override
	public void onClick(View v) {
		switch ( v.getId() ){
			case R.id.btn_report:
				isFlag = true;
				if ( checkMandatoryFields() ) {
					isFlag = false;
					dbHelper.getStudentAttendanceReport(report);
				} else {
					activity.toast(getString(R.string.check_mandatory));
				}
				break;
		}
	}

	public class StudentReport {
		private String studentEmailId;
		private String month;
		private String year;

		public String getStudentEmailId() {
			return studentEmailId;
		}

		public void setStudentEmailId(String studentEmailId) {
			this.studentEmailId = studentEmailId;
		}

		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}
	}



	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if ( context instanceof ViewReportsActivity ) {
			activity = (ViewReportsActivity) context;
		}
		else {
			throw new ClassCastException("Wrong activity is typecast");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		activity = null;
	}
}
