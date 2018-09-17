package com.attendance.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.attendance.R;
import com.attendance.adapters.LoginAdapter;
import com.attendance.fragments.AdminLoginFragment;
import com.attendance.fragments.TeacherLoginFragment;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    private String[] title = {"Admin Login", "Teacher Login"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setUpView();
	}

    private void setUpView() {
        ViewPager viewPager = findViewById(R.id.loginViewpager);
        setUpViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.loginTabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {
        LoginAdapter adapter = new
                LoginAdapter(getSupportFragmentManager());
        adapter.addFragment(AdminLoginFragment.newInstance(), title[0]);
        adapter.addFragment(TeacherLoginFragment.newInstance(), title[1]);
        viewPager.setAdapter(adapter);
    }
}

