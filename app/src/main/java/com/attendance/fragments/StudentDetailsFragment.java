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
import com.attendance.adapters.ViewEditDetailsAdapter;
import com.attendance.data_models.DataModel;

import java.util.ArrayList;

public class StudentDetailsFragment extends Fragment {

	private static final String TAG = "STUDENT_DETAILS";
	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private ViewDetailsActivity activity;
	private ArrayList<DataModel > dataList = new ArrayList<>();

	public static StudentDetailsFragment newInstance() {
		return new StudentDetailsFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_student_details, container, false);
		recyclerView = view.findViewById(R.id.rcvEditStudent);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(activity);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		//todo: dummy data
		dataList.add(new DataModel("Arun", "Cse"));
		dataList.add(new DataModel("Navjot", "Cse"));
		dataList.add(new DataModel("Rupinder", "Ece"));
		dataList.add(new DataModel("Harman", "Me"));
		dataList.add(new DataModel("Chanchal", "Ece"));
		dataList.add(new DataModel("Arvind", "Me"));
		dataList.add(new DataModel("Savdeep Singh", "Me"));
		dataList.add(new DataModel("Neeru", "Case"));
		dataList.add(new DataModel("Neeru", "Case"));
		dataList.add(new DataModel("Neeru", "Case"));
		dataList.add(new DataModel("Neeru", "Case"));

		adapter = new ViewEditDetailsAdapter(activity, dataList, TAG);
		recyclerView.setAdapter(adapter);
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
