package com.attendance.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddDetailsViewPagerAdapter extends FragmentStatePagerAdapter{
	private final List<Fragment> mFragmentList = new ArrayList<>();
	private final List<String> mFragmentTittle = new ArrayList<>();

	public AddDetailsViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return mFragmentList.get(position);
	}

	@Override
	public int getCount() {
		return mFragmentList.size();
	}

	public void addFragment( Fragment fm, String tittle) {
		mFragmentList.add(fm);
		mFragmentTittle.add(tittle);
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return mFragmentTittle.get (position);
	}
}
