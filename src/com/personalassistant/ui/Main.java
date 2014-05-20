package com.personalassistant.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import com.personalassistant.R;
import com.personalassistant.ui.navigation.NavigationDrawerFragment;
import com.personalassistant.ui.navigation.NavigationDrawerFragment.NavigationDrawerCallbacks;
import com.personalassistant.ui.navigation.NavigationMenuItem;

public class Main extends Activity implements NavigationDrawerCallbacks {
	private NavigationDrawerFragment mNavigationDrawerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(NavigationMenuItem item) {
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, item.getFragment()).commit();
	}
}
