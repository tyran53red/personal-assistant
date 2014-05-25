package com.personalassistant.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
					String[] splitDescription = split(description, '[');
					
					if (splitDescription.length > 1 && splitDescription[0].startsWith("user-name") && splitDescription[1].startsWith("status")) {
						String userName = splitDescription[0].substring("user-name".length() + 1, splitDescription[0].length() - 1);
						LecturersListItem item = findItem(userName);
						
						if (item == null) {
							item = new LecturersListItem(userName, activity.getResources());
							lecturersListItems.add(item);
						}
						
						String statusIdentifier = splitDescription[1].substring("status".length() + 1, splitDescription[1].length() - 1);
						
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
					String[] splitDescription = split(description, '[');
					
					if (splitDescription.length > 2 && splitDescription[0].startsWith("user-name") && splitDescription[1].startsWith("corp")
							 && splitDescription[2].startsWith("number")) {
						String userName = splitDescription[0].substring("user-name".length() + 1, splitDescription[0].length() - 1);
						LecturersListItem item = findItem(userName);
						
						if (item == null) {
							item = new LecturersListItem(userName, activity.getResources());
							lecturersListItems.add(item);
						}
						
						String corp = splitDescription[1].substring("corp".length() + 1, splitDescription[1].length() - 1);
						String number = splitDescription[2].substring("number".length() + 1, splitDescription[2].length() - 1);
						
						item.setAuditory(new Auditory(Integer.parseInt(number), Integer.parseInt(corp)));
					}
				}
			}
		}
		
		onDayLoaded(lecturersListItems);
	}
	
	private String[] split(String string, char pattern) {
		List<String> result = new ArrayList<String>();
		
		String tempString = new String();
		for (char c : string.toCharArray()) {
			if (c == pattern) {
				if (tempString.length() > 0) {
					result.add(new String(tempString));
				}
				
				tempString = new String();
			} else {
				tempString += c;
			}
		}
		
		if (tempString.length() > 0) {
			result.add(new String(tempString));
		}
		
		return result.toArray(new String[0]);
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
