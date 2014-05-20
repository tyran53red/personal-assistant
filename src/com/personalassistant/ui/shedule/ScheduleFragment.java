package com.personalassistant.ui.shedule;

import java.util.Calendar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.personalassistant.R;
import com.personalassistant.ui.widget.SingleTabWidget;

public class ScheduleFragment extends Fragment implements SingleTabWidget.OnTabChangedListener {
	private ViewPager pager = null;
	private SingleTabWidget tabWidget = null;
	private HorizontalScrollView tabsScroll = null;
	
	private ScheduleFragmentPagerAdapter adapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.schedule_fragment, container, false);
		
		adapter = new ScheduleFragmentPagerAdapter(getChildFragmentManager());
		
		tabsScroll = (HorizontalScrollView)inflate.findViewById(R.id.schedule_tabs_scroll);
		tabWidget = (SingleTabWidget)inflate.findViewById(R.id.schedule_tabs);
		tabWidget.setLayout(R.layout.single_tab_indicator);
		tabWidget.setOnTabChangedListener(this);
		
		pager = (ViewPager)inflate.findViewById(R.id.pager);
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	tabWidget.setCurrentTab(position);
            	View selectedTab = tabWidget.getChildAt(position);
            	tabsScroll.scrollTo(selectedTab.getLeft(), 0);
            }
        });
		
		addDay(Calendar.getInstance());
		tabWidget.setCurrentTab(0);
		
		return inflate;
	}
	
	private void addDay(Calendar calendar) {
		ScheduleDayItem addedDay = adapter.addDay(calendar);
		tabWidget.addTab(addedDay.getTitle(getResources()));
	}

	@Override
	public void onTabChanged(int tabIndex) {
		pager.setCurrentItem(tabIndex);
	}
}
