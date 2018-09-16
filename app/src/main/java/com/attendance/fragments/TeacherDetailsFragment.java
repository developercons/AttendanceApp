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
import com.attendance.adapters.EditTeacherDetailsAdapter;
import com.attendance.data_models.TeacherData;
import com.attendance.database.MyDBHelper;

import java.util.ArrayList;

public class TeacherDetailsFragment extends Fragment {
	private static final String TAG = "TEACHER_DETAILS";
	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private ViewDetailsActivity activity;
	private ArrayList<TeacherData> dataList = new ArrayList<>();
	private MyDBHelper dbHelper;

	public static TeacherDetailsFragment newInstance() {
		return new TeacherDetailsFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_teacher_details, container, false);
		dbHelper = MyDBHelper.getInstance(activity);
		recyclerView = view.findViewById(R.id.rcvEditTeacher);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(activity);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		if ( !dbHelper.getTeacherData().isEmpty() ) {
			dataList.clear();
			dataList.addAll(dbHelper.getTeacherData());
			adapter = new EditTeacherDetailsAdapter(activity, dataList);
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
