package com.personalassistant.ui.location;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.personalassistant.R;
import com.personalassistant.ui.widget.SingleTabWidget;

public class LocationFragment extends Fragment implements SingleTabWidget.OnTabChangedListener {
	private ViewPager pager = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.location_fragment, container, false);
		
		LocationFragmentPagerAdapter locationFragmentPagerAdapter = new LocationFragmentPagerAdapter(getChildFragmentManager(), createItems());
		
		final SingleTabWidget tabWidget = (SingleTabWidget)inflate.findViewById(R.id.location_tabs);
		tabWidget.setLayout(R.layout.single_tab_indicator);
		tabWidget.setOnTabChangedListener(this);
		
		pager = (ViewPager)inflate.findViewById(R.id.pager);
		pager.setAdapter(locationFragmentPagerAdapter);
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	tabWidget.setCurrentTab(position);
            }
        });
		
		for (int i = 0; i < locationFragmentPagerAdapter.getCount(); i++) {
			LocationTabItem item = locationFragmentPagerAdapter.getTabItem(i);
			tabWidget.addTab(item.getTitle(), i);
		}
		
		tabWidget.setCurrentTab(0);
		
		return inflate;
	}
	
	private List<LocationTabItem> createItems() {
		List<LocationTabItem> items = new ArrayList<LocationTabItem>();
		
		items.add(new LocationTabItem() {
			@Override
			public String getTitle() {
				return getString(R.string.location_tab_1);
			}
			
			@Override
			public Fragment getFragment() {
				return new LocationTabContent();
			}

			@Override
			public int getPosition() {
				return 0;
			}
		});
		items.add(new LocationTabItem() {
			@Override
			public String getTitle() {
				return getString(R.string.location_tab_2);
			}
			
			@Override
			public Fragment getFragment() {
				return new LocationTabContent();
			}

			@Override
			public int getPosition() {
				return 1;
			}
		});
		
		return items;
	}

	@Override
	public void onTabChanged(int tabIndex) {
		pager.setCurrentItem(tabIndex);
	}
}
