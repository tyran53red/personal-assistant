package com.personalassistant.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.CalendarContract.Events;

import com.personalassistant.App;
import com.personalassistant.model.Auditory;
import com.personalassistant.model.Status;
import com.personalassistant.ui.lecturers.LecturersListItem;
import com.personalassistant.ui.util.DataToolkit;

public abstract class LoadLecturersTask extends CalendarAsyncTask {
	public static final String[] EVENT_PROJECTION = new String[] {
		Events._ID,
		Events.DTSTART,
		Events.DTEND,
		Events.RRULE,
		Events.TITLE,
		Events.DESCRIPTION
	};
	
	private static final int DESCRIPTION = 5;
	
	public static final String ORDER = Events.DTSTART;
	public static final String WHERE_STATUS = "(" + Events.CALENDAR_ID + " = ?)";
	public static final String WHERE_LOCATION = "(" + Events.CALENDAR_ID + " = ?)";

	private Calendar calendarStart = null;
	private Calendar calendarEnd = null;
	private Activity activity = null;
	
	private List<LecturersListItem> lecturersListItems = new ArrayList<LecturersListItem>();
	  
	public LoadLecturersTask(Activity activity, Calendar calendar) {
		this.activity = activity;
		this.calendarStart = Calendar.getInstance();
		this.calendarEnd = Calendar.getInstance();
		
		this.calendarStart.set(
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				0, 0, 0);
		
		this.calendarEnd.set(
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				11, 59, 59);
	}

	@Override
	protected void doInBackground() throws Exception {
		SharedPreferences preferences = activity.getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		ContentResolver contentResolver = activity.getContentResolver();
		Cursor cursor = null;
		
		if (preferences.contains(App.SELECTED_STATUS)) {
			long value = preferences.getLong(App.SELECTED_STATUS, 0);
			
			cursor = contentResolver.query(Events.CONTENT_URI,
				EVENT_PROJECTION,
				WHERE_STATUS,
				new String[] {
					String.valueOf(value),
				},
				ORDER);
			
			
			
			while(cursor.moveToNext()) {
				String description = cursor.getString(DESCRIPTION);
				
				if (description != null && description.length() > 0) {
					Map<String, String> attributes = DataToolkit.getEventAttributes(description);
					
					if (attributes.containsKey(CalendarsConstants.USER_NAME) && attributes.containsKey(CalendarsConstants.STATUS)) {
						String userName = attributes.get(CalendarsConstants.USER_NAME);
						LecturersListItem item = findItem(userName);
						
						if (item == null) {
							item = new LecturersListItem(userName, activity.getResources());
							lecturersListItems.add(item);
						}
						
						String statusIdentifier = attributes.get(CalendarsConstants.STATUS);
						
						Status status = null;
						for (Status s : App.getDefaultStatuses(activity)) {
							if (s.getIdentifier().equals(statusIdentifier)) {
								status = s;
								
								break;
							}
						}
						
						item.setStatus(status);
					}
				}
			}
		}
		
		if (preferences.contains(App.SELECTED_LOCATION)) {
			long value = preferences.getLong(App.SELECTED_LOCATION, 0);
			
			cursor = contentResolver.query(Events.CONTENT_URI,
				EVENT_PROJECTION,
				WHERE_LOCATION,
				new String[] {
					String.valueOf(value),
				},
				ORDER);
			
			
			
			while(cursor.moveToNext()) {
				String description = cursor.getString(DESCRIPTION);
				
				if (description != null && description.length() > 0) {
					Map<String, String> attributes = DataToolkit.getEventAttributes(description);
					
					if (attributes.containsKey(CalendarsConstants.USER_NAME) && attributes.containsKey(CalendarsConstants.CORP) && attributes.containsKey(CalendarsConstants.NUMBER)) {
						String userName = attributes.get(CalendarsConstants.USER_NAME);
						LecturersListItem item = findItem(userName);
						
						if (item == null) {
							item = new LecturersListItem(userName, activity.getResources());
							lecturersListItems.add(item);
						}
						
						String corp = attributes.get(CalendarsConstants.CORP);
						String number = attributes.get(CalendarsConstants.NUMBER);
						
						item.setAuditory(new Auditory(Integer.parseInt(number), Integer.parseInt(corp)));
					}
				}
			}
		}
		
		onDayLoaded(lecturersListItems);
	}
	
	private LecturersListItem findItem(String userName) {
		for (LecturersListItem item : lecturersListItems) {
			if (item.getUserName().equals(userName)) {
				return item;
			}
		}
		
		return null;
	}

	protected abstract void onDayLoaded(List<LecturersListItem> lessons);
}
