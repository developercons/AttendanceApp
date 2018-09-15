package com.attendance.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attendance.R;

public class AttendanceViewReportFragment extends Fragment {

	public static AttendanceViewReportFragment newInstance() {
		return new AttendanceViewReportFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_attendance_view_report, container, false);
	}
}
