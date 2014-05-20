package com.personalassistant.services;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;

import com.personalassistant.App;
import com.personalassistant.model.AbstractLesson;

public abstract class LoadScheduleTask extends CalendarAsyncTask {
	private Calendar calendar = null;
	private Activity activity = null;
	
	public static final String[] EVENT_PROJECTION = new String[] {
	    Calendars._ID,
	    Calendars.CALENDAR_DISPLAY_NAME,
	};
	  
	private static final int PROJECTION_ID_INDEX = 0;
	private static final int PROJECTION_DISPLAY_NAME_INDEX = 1;
	
	public LoadScheduleTask(Activity activity, Calendar calendar) {
		this.activity = activity;
		this.calendar = calendar;
	}

	@Override
	protected void doInBackground() throws Exception {
		SharedPreferences preferences = activity.getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		if (preferences.contains(App.SELECTED_SCHEDULE)) {
			int value = preferences.getInt(App.SELECTED_SCHEDULE, 0);
		
			ContentResolver contentResolver = activity.getContentResolver();
			
			Uri uri = Events.CONTENT_URI;   
			
			String[] proj = new String[] { Events._ID, Events.DTSTART, Events.DTEND, Events.RRULE, Events.TITLE };
			Cursor cursor = contentResolver.query(uri, proj, Calendars._ID + " = ? ", new String[] { Long.toString(value) }, null);
			if (cursor.moveToNext()) {
				
				System.out.println(cursor.getString(4));
			}
		
		}
	}

	protected abstract void onDayLoaded(List<AbstractLesson> lessons);
}
