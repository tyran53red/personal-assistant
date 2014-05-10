package com.personalassistant.ui.location;

import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

public class LocationFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<LocationTabItem> items = null;

	public LocationFragmentPagerAdapter(FragmentManager fm, List<LocationTabItem> items) {
		super(fm);
		this.items = items;
	}

	@Override
	public Fragment getItem(int i) {
		return items.get(i).getFragment();
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return items.get(position).getTitle();
	}
	
	public LocationTabItem getTabItem(int position) {
		return items.get(position);
	}
}
