package com.attendance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.attendance.R;

public class NavigationActivity extends AppCompatActivity implements View.OnClickListener {

	private DrawerLayout drawer;
	public Toolbar toolbar;
	public ConstraintLayout clParent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		setUpView();
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

	}

	private void setUpView() {
		drawer = findViewById(R.id.drawer_layout);
		clParent = findViewById(R.id.clContent);
		TextView tvDashboard = findViewById(R.id.tvDashboard);
		TextView tvAddDetails = findViewById(R.id.tvAddDetails);
		TextView tvViewDetails = findViewById(R.id.tvViewDetails);
		TextView tvViewReports = findViewById(R.id.tvViewReport);
		Button btnLogout = findViewById(R.id.btnLogout);

		tvDashboard.setOnClickListener(this);
		tvAddDetails.setOnClickListener(this);
		tvViewDetails.setOnClickListener(this);
		tvViewReports.setOnClickListener(this);
		btnLogout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		drawer.closeDrawer(GravityCompat.START);
		Intent intent;
		switch ( v.getId() ) {
			case R.id.tvDashboard:
				intent = new Intent(this, DashboardActivity.class);
				startActivity(intent);
				break;

			case R.id.tvAddDetails:
				intent = new Intent(this, AddDetailsActivity.class);
				startActivity(intent);
				finish();
				break;

			case R.id.tvViewDetails:
				intent = new Intent(this, ViewDetailsActivity.class);
				startActivity(intent);
				finish();
				break;

			case R.id.tvViewReport:
				intent = new Intent(this, ViewReportsActivity.class);
				startActivity(intent);
				finish();
				break;
		}
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if ( drawer.isDrawerOpen(GravityCompat.START) ) {
			drawer.closeDrawer(GravityCompat.START);
		}
		else {
			super.onBackPressed();
		}
	}
}
