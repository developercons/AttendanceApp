package com.attendance.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attendance.R;

public class AddTeacherFragment extends Fragment {

	public static AddTeacherFragment newInstance() {
		return new AddTeacherFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_teacher, container, false);
		return view;
	}
}
