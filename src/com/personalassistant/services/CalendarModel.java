package com.personalassistant.services;

import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarModel {
	private final Map<String, CalendarInfo> calendars = new HashMap<String, CalendarInfo>();
	private static CalendarModel instance = null;

	public static CalendarModel getInstance() {
		if (instance == null) {
			instance = new CalendarModel();
		}
		
		return instance;
	}

	public int size() {
		synchronized (calendars) {
			return calendars.size();
		}
	}

	public void remove(String id) {
		synchronized (calendars) {
			calendars.remove(id);
		}
	}

	public CalendarInfo get(String id) {
		synchronized (calendars) {
			return calendars.get(id);
		}
	}

	public void add(Calendar calendarToAdd) {
		synchronized (calendars) {
			CalendarInfo found = get(calendarToAdd.getId());
			if (found == null) {
				calendars.put(calendarToAdd.getId(), new CalendarInfo(
						calendarToAdd));
			} else {
				found.update(calendarToAdd);
			}
		}
	}

	public void add(CalendarListEntry calendarToAdd) {
		synchronized (calendars) {
			CalendarInfo found = get(calendarToAdd.getId());
			if (found == null) {
				calendars.put(calendarToAdd.getId(), new CalendarInfo(
						calendarToAdd));
			} else {
				found.update(calendarToAdd);
			}
		}
	}

	public void reset(List<CalendarListEntry> calendarsToAdd) {
		synchronized (calendars) {
			calendars.clear();
			for (CalendarListEntry calendarToAdd : calendarsToAdd) {
				add(calendarToAdd);
			}
		}
	}

	public CalendarInfo[] toSortedArray() {
		synchronized (calendars) {
			List<CalendarInfo> result = new ArrayList<CalendarInfo>();
			for (CalendarInfo calendar : calendars.values()) {
				result.add(calendar.clone());
			}
			Collections.sort(result);
			return result.toArray(new CalendarInfo[0]);
		}
	}
}
