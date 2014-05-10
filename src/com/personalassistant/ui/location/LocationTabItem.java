package com.personalassistant.ui.location;

import android.app.Fragment;

public interface LocationTabItem {

	public String getTitle();
	public Fragment getFragment();
	public int getPosition();
}
