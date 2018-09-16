package com.attendance.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.attendance.R;
import com.attendance.adapters.AddDetailsViewPagerAdapter;
import com.attendance.fragments.AddClassFragment;
import com.attendance.fragments.AddStudentFragment;
import com.attendance.fragments.AddTeacherFragment;

import java.util.ArrayList;

public class AddDetailsActivity extends NavigationActivity {
	private String[] title = {"Teacher", "Class", "Student"};

	public ArrayList<String> qualificationList = new ArrayList<>();
	public ArrayList<String> semesterList = new ArrayList<>();
	public ArrayList<String> coursesList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = getLayoutInflater().inflate(R.layout.activity_add_details, clParent, false);
		clParent.addView(view);
		toolbar.setSubtitle(getString(R.string.add_details));
		setUpView();

		if(qualificationList.size() == 0) {
			qualificationList.add("BSC-IT");
			qualificationList.add("BCA");
			qualificationList.add("MSC-IT");
			qualificationList.add("B-Tech");
			qualificationList.add("BBA");
			qualificationList.add("B-COM");
		}

		if(semesterList.size() == 0) {
		    semesterList.add("1");
		    semesterList.add("2");
		    semesterList.add("3");
		    semesterList.add("4");
		    semesterList.add("5");
		    semesterList.add("6");
		    semesterList.add("7");
		    semesterList.add("8");
        }

        if(coursesList.size() == 0) {
		    coursesList.add("2D-Design");
		    coursesList.add("3D-Design");
		    coursesList.add("Data Analyst");
		    coursesList.add("Data Science");
		    coursesList.add("Big Data");
		    coursesList.add("IOT");
		    coursesList.add("AI");
        }
	}

	private void setUpView() {
		ViewPager viewPager = findViewById(R.id.addDetailsViewpager);
		setUpViewPager(viewPager);
		TabLayout tabLayout = findViewById(R.id.addDetailsTabs);
		tabLayout.setupWithViewPager(viewPager);
	}

	private void setUpViewPager(ViewPager viewPager) {
		AddDetailsViewPagerAdapter adapter = new
				AddDetailsViewPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(AddTeacherFragment.newInstance(), title[0]);
		adapter.addFragment(AddClassFragment.newInstance(), title[1]);
		adapter.addFragment(AddStudentFragment.newInstance(), title[2]);
		viewPager.setAdapter(adapter);
	}

	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
