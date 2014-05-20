package com.personalassistant;

import java.util.Map;

import android.os.Handler;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

public class App {
	public enum CalendarType {
		SCHEDULE;
		
		public static CalendarType checkCalendarType(String name) {
			if (name.startsWith("schedule")) {
				return SCHEDULE;
			}
			
			return null;
		}
	}
	
	public static String SHARED_PREFERENCES_NAME = "PASPF";
	public static String SELECTED_SCHEDULE = "selected-schedule";
	
	private static GoogleAccountCredential credential = null;
	private static Map<CalendarType, Integer> calendars = null;
	private static Handler handler = null;

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
}
