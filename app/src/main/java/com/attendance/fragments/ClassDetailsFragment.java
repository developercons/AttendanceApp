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
import com.attendance.data_models.ClassData;

import java.util.ArrayList;

public class ClassDetailsFragment extends Fragment {

	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private ViewDetailsActivity activity;
	private ArrayList<ClassData> dataList;

	public static ClassDetailsFragment newInstance() {
		return new ClassDetailsFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_class_details, container, false);

		recyclerView = view.findViewById(R.id.rcvEditClass);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(activity);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		dataList = new ArrayList<>();
		//todo: dummy data
		dataList.add(new ClassData("Arun", "Cse"));
		dataList.add(new ClassData("Navjot", "Cse"));
		dataList.add(new ClassData("Rupinder", "Ece"));
		dataList.add(new ClassData("Harman", "Me"));
		dataList.add(new ClassData("Chanchal", "Ece"));
		dataList.add(new ClassData("Arvind", "Me"));
		dataList.add(new ClassData("Savdeep Singh", "Me"));
		dataList.add(new ClassData("Neeru", "Case"));
		dataList.add(new ClassData("Neeru", "Case"));
		dataList.add(new ClassData("Neeru", "Case"));
		dataList.add(new ClassData("Neeru", "Case"));

		adapter = new EditClassDetailsAdapter(activity, dataList);
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
