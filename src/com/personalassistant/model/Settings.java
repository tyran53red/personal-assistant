package com.personalassistant.model;

import java.util.List;

public class Settings {
	private List<SettingsCalendarItem> scheduleItems = null;
	private List<SettingsCalendarItem> locationItems = null;
	
	public Settings() {
		
	}

	public List<SettingsCalendarItem> getScheduleItems() {
		return scheduleItems;
	}

	public void setScheduleItems(List<SettingsCalendarItem> scheduleItems) {
		this.scheduleItems = scheduleItems;
	}

	public List<SettingsCalendarItem> getLocationItems() {
		return locationItems;
	}

	public void setLocationItems(List<SettingsCalendarItem> locationItems) {
		this.locationItems = locationItems;
	}
}
