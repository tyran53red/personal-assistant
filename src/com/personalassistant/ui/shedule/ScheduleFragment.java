package com.personalassistant.ui.shedule;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.personalassistant.R;
import com.personalassistant.ui.widget.SingleTabWidget;

public class ScheduleFragment extends Fragment implements SingleTabWidget.OnTabChangedListener {
	private ViewPager pager = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.schedule_fragment, container, false);
		
		ScheduleFragmentPagerAdapter adapter = new ScheduleFragmentPagerAdapter(getChildFragmentManager());
		
		final SingleTabWidget tabWidget = (SingleTabWidget)inflate.findViewById(R.id.schedule_tabs);
		tabWidget.setLayout(R.layout.single_tab_indicator);
		tabWidget.setOnTabChangedListener(this);
		
		pager = (ViewPager)inflate.findViewById(R.id.pager);
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	tabWidget.setCurrentTab(position);
            }
        });
		
		for (int i = 0; i < adapter.getCount(); i++) {
			ScheduleDayItem item = adapter.getTabItem(i);
			tabWidget.addTab(item.getTitle(getResources()));
		}
		
		tabWidget.setCurrentTab(0);
		
		return inflate;
	}

	@Override
	public void onTabChanged(int tabIndex) {
		pager.setCurrentItem(tabIndex);
	}
}
