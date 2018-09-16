package com.attendance.activities;

import android.os.Bundle;
import android.view.View;

import com.attendance.R;

public class DashboardActivity extends NavigationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = getLayoutInflater().inflate(R.layout.activity_dashboard, clParent, false);
		clParent.addView(view);

	}
}
