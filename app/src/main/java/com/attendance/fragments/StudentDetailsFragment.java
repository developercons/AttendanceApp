package com.attendance.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attendance.R;
import com.attendance.activities.ViewDetailsActivity;
import com.attendance.adapters.EditClassDetailsAdapter;
import com.attendance.adapters.EditStudentDetailsAdapter;
import com.attendance.data_models.StudentData;
import com.attendance.database.MyDBHelper;

import java.util.ArrayList;

public class StudentDetailsFragment extends Fragment {

	private static final String TAG = "STUDENT_DETAILS";
	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private ViewDetailsActivity activity;
	private MyDBHelper dbHelper;
	private ArrayList<StudentData > dataList = new ArrayList<>();

	public static StudentDetailsFragment newInstance() {
		return new StudentDetailsFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_student_details, container, false);
		dbHelper = MyDBHelper.getInstance(activity);
		recyclerView = view.findViewById(R.id.rcvEditStudent);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(activity);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		if ( !dbHelper.getStudentData().isEmpty() ) {
			dataList.clear();
			dataList.addAll(dbHelper.getStudentData());
			adapter = new EditStudentDetailsAdapter(activity, dataList);
			recyclerView.setAdapter(adapter);
		}
		return view;
	}


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if ( context instanceof ViewDetailsActivity ) {
			activity = (ViewDetailsActivity) context;
		} else {
			throw  new ClassCastException("Wrong activity found");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		activity = null;
	}
}
