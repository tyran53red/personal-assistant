package com.personalassistant.model;

import java.util.List;

public class Settings {
	private List<SettingsScheduleItem> scheduleItems = null;
	
	public Settings() {
		
	}

	public List<SettingsScheduleItem> getScheduleItems() {
		return scheduleItems;
	}

	public void setScheduleItems(List<SettingsScheduleItem> scheduleItems) {
		this.scheduleItems = scheduleItems;
	}
}
