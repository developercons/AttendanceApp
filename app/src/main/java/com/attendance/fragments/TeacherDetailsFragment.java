package com.attendance.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attendance.R;

public class TeacherDetailsFragment extends Fragment {

	public static TeacherDetailsFragment newInstance() {
		return new TeacherDetailsFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_teacher_details, container, false);
		return view;
	}
}
