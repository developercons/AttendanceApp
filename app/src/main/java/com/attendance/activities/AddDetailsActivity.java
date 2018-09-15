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

public class AddDetailsActivity extends NavigationActivity {
	private String[] tittle = {"Teacher", "Class", "Student"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = getLayoutInflater().inflate(R.layout.activity_add_details, clParent, false);
		clParent.addView(view);
		toolbar.setSubtitle(getString(R.string.add_details));
		setUpView();
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
		adapter.addFragment(AddTeacherFragment.newInstance(), tittle[0]);
		adapter.addFragment(AddClassFragment.newInstance(), tittle[1]);
		adapter.addFragment(AddStudentFragment.newInstance(), tittle[2]);
		viewPager.setAdapter(adapter);
	}

	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
