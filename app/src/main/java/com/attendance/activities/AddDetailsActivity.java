package com.attendance.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.attendance.R;
import com.attendance.adapters.AddDetailsViewPagerAdapter;
import com.attendance.fragments.AddClassFragment;
import com.attendance.fragments.AddStudentFragment;
import com.attendance.fragments.AddTeacherFragment;
import com.attendance.helper_classes.PermissionUtils;

import java.util.ArrayList;

public class AddDetailsActivity extends NavigationActivity {
	private String[] title = {"Teacher", "Class", "Student"};

	public ArrayList<String> qualificationList = new ArrayList<>();
	public ArrayList<String> semesterList = new ArrayList<>();
	public ArrayList<String> coursesList = new ArrayList<>();
	public static int PERMISSION_REQUEST_CODE = 100;
    public String[] permissionList = {Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = getLayoutInflater().inflate(R.layout.activity_add_details, clParent, false);
		clParent.addView(view);
		toolbar.setSubtitle(getString(R.string.add_details));
		setUpView();
	}

    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionUtils.isAndroidVersionMorHigher()) {
            if (!PermissionUtils.isGranted()) {
                PermissionUtils.permissionGranted(this, permissionList, PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean isGranted = true;
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        isGranted = true;
                    } else {
                        isGranted = false;
                        break;
                    }
                }

                boolean issSecondTimePermission = PermissionUtils.isShouldShowRequestPermissionRationale(this, permissionList);

                if (isGranted) {
                    PermissionUtils.proceedAfterPermission(this, true);
                } else if (issSecondTimePermission) {
                    PermissionUtils.secondTimePermission(this, permissionList, PERMISSION_REQUEST_CODE);
                } else {
                    PermissionUtils.openSettings(this);
                }
            }
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
