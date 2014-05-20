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
	}
	
	public void setItems(List<ScheduleDayItem> items) {
		this.items.addAll(items);
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

	public ScheduleDayItem addDay(Calendar calendar) {
		ScheduleDayItem object = new ScheduleDayItem(calendar);
		items.add(object);
		notifyDataSetChanged();
		
		return object;
	}
}
