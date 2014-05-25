package com.personalassistant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Handler;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.personalassistant.model.Status;

public class App {
	public enum CalendarType {
		SCHEDULE, LOCATION, STATUS;
		
		public static CalendarType checkCalendarType(String name) {
			if (name.startsWith("schedule")) {
				return SCHEDULE;
			} else if (name.startsWith("location")) {
				return LOCATION;
			} else if (name.startsWith("status")) {
				return STATUS;
			}
			
			return null;
		}
	}
	
	public static String SHARED_PREFERENCES_NAME = "PASPF";
	public static String SELECTED_SCHEDULE = "selected-schedule";
	public static String SELECTED_LOCATION = "selected-location";
	public static String SELECTED_STATUS = "selected-status";
	
	private static GoogleAccountCredential credential = null;
	private static Map<CalendarType, Integer> calendars = null;
	private static Handler handler = null;

	private static List<Status> defaultStatuses = null;
	
	public static GoogleAccountCredential getCredential() {
		return credential;
	}

	public static void setCredential(GoogleAccountCredential credential) {
		App.credential = credential;
	}

	public static Map<CalendarType, Integer> getCalendars() {
		return calendars;
	}

	public static void setCalendars(Map<CalendarType, Integer> calendars) {
		App.calendars = calendars;
	}
	
	public static int getCalendar(CalendarType calendarType) {
		return calendars.get(calendarType);
	}

	public static Handler getHandler() {
		if (handler == null) {
			handler = new Handler();
		}
		
		return handler;
	}

	public static List<Status> getDefaultStatuses(Activity activity) {
		if (defaultStatuses == null) {
			defaultStatuses = new ArrayList<Status>();
			
			defaultStatuses.add(new Status("status_lab", activity.getString(R.string.status_lab)));
			defaultStatuses.add(new Status("status_lec", activity.getString(R.string.status_lec)));
			defaultStatuses.add(new Status("status_module", activity.getString(R.string.status_module)));
			defaultStatuses.add(new Status("status_check_module", activity.getString(R.string.status_check_module)));
			defaultStatuses.add(new Status("status_exam", activity.getString(R.string.status_exam)));
			defaultStatuses.add(new Status("status_check_exam", activity.getString(R.string.status_check_exam)));
			defaultStatuses.add(new Status("status_free_time", activity.getString(R.string.status_free_time)));
			defaultStatuses.add(new Status("status_other", activity.getString(R.string.status_other)));
		}
		
		return defaultStatuses;
	}
}
