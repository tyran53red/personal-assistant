package com.personalassistant.ui.shedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

public class ScheduleFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<ScheduleDayItem> items = new ArrayList<ScheduleDayItem>();
	
	public ScheduleFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		
		items.add(new ScheduleDayItem(Calendar.getInstance()));
		items.add(new ScheduleDayItem(Calendar.getInstance()));
		items.add(new ScheduleDayItem(Calendar.getInstance()));
		items.add(new ScheduleDayItem(Calendar.getInstance()));
		items.add(new ScheduleDayItem(Calendar.getInstance()));
	}

	@Override
	public Fragment getItem(int i) {
		return items.get(i).getFragment();
	}

	@Override
	public int getCount() {
		return items.size();
	}

	public ScheduleDayItem getTabItem(int i) {
		return items.get(i);
	}
}
