package com.attendance.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.attendance.R;
import com.attendance.adapters.AddDetailsViewPagerAdapter;
import com.attendance.adapters.ViewDetailsViewPagerAdapter;
import com.attendance.fragments.AddClassFragment;
import com.attendance.fragments.AddStudentFragment;
import com.attendance.fragments.AddTeacherFragment;
import com.attendance.fragments.ClassDetailsFragment;
import com.attendance.fragments.StudentDetailsFragment;
import com.attendance.fragments.TeacherDetailsFragment;

public class ViewDetailsActivity extends NavigationActivity {
	private String[] tittle = {"Teacher", "Class", "Student"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = getLayoutInflater().inflate(R.layout.activity_view_details, clParent, false);
		clParent.addView(view);
		toolbar.setSubtitle(getString(R.string.view_edit_details));
		setUpView();
	}

	private void setUpView() {
		ViewPager viewPager = findViewById(R.id.viewDetailsViewpager);
		setUpViewPager(viewPager);
		TabLayout tabLayout = findViewById(R.id.viewDetailsTabs);
		tabLayout.setupWithViewPager(viewPager);
	}

	private void setUpViewPager(ViewPager viewPager) {
		ViewDetailsViewPagerAdapter adapter = new
				ViewDetailsViewPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(TeacherDetailsFragment.newInstance(), tittle[ 0 ]);
		adapter.addFragment(ClassDetailsFragment.newInstance(), tittle[ 1 ]);
		adapter.addFragment(StudentDetailsFragment.newInstance(), tittle[ 2 ]);
		viewPager.setAdapter(adapter);
	}

	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
