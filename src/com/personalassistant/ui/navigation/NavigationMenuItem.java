package com.personalassistant.ui.navigation;

import android.app.Fragment;

public abstract class NavigationMenuItem {
	public abstract Fragment getFragment();
	public abstract String getName();
	public abstract int getIcon();

	@Override
	public String toString() {
		return getName();
	}
}
