package com.attendance.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.attendance.R;
import com.attendance.adapters.AddDetailsViewPagerAdapter;
import com.attendance.adapters.ViewReportViewPagerAdapter;
import com.attendance.fragments.AddClassFragment;
import com.attendance.fragments.AddStudentFragment;
import com.attendance.fragments.AddTeacherFragment;
import com.attendance.fragments.AttendanceViewReportFragment;
import com.attendance.fragments.StudentViewReportFragment;

public class ViewReportsActivity extends NavigationActivity {
	private String[] tittle = {"Attendance" , "Student"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = getLayoutInflater().inflate(R.layout.activity_view_reports, clParent, false);
		clParent.addView(view);
		toolbar.setSubtitle(getString(R.string.view_reports));
		setUpView();
	}

	private void setUpView() {
		ViewPager viewPager = findViewById(R.id.viewReportsViewpager);
		setUpViewPager(viewPager);
		TabLayout tabLayout = findViewById(R.id.viewReportsTabs);
		tabLayout.setupWithViewPager(viewPager);
	}

	private void setUpViewPager(ViewPager viewPager) {
		ViewReportViewPagerAdapter adapter = new
				ViewReportViewPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(AttendanceViewReportFragment.newInstance(), tittle[0]);
		adapter.addFragment(StudentViewReportFragment.newInstance(), tittle[1]);
		viewPager.setAdapter(adapter);
	}

	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
