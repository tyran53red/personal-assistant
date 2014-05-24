package com.personalassistant.model;

public class SettingsCalendarItem {
	private String name = null;
	private long id = -1;
	
	public SettingsCalendarItem(String name, long id) {
		setName(name);
		setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return getName();
	}
}
