package com.personalassistant.services;

import java.util.TimeZone;

import com.personalassistant.App;
import com.personalassistant.model.Auditory;
import com.personalassistant.model.User;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.CalendarContract.Events;

public abstract class CreateLocationEvent extends CalendarAsyncTask {
	private Activity activity = null;
	private Auditory auditory = null;
	
	private static long EVENT_PERIOD = 1000 * 60 * 60;
	
	public CreateLocationEvent(Activity activity, Auditory auditory) {
		this.activity = activity;
		this.auditory = auditory;
	}

	@Override
	protected void doInBackground() throws Exception {
		SharedPreferences preferences = activity.getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		if (preferences.contains(App.SELECTED_LOCATION)) {
			long value = preferences.getLong(App.SELECTED_LOCATION, 0);
			
			ContentValues eventValues = new ContentValues();
			
			eventValues.put(Events.CALENDAR_ID, value);
			eventValues.put(Events.TITLE, User.getUser().getName());
			eventValues.put(Events.DESCRIPTION,
					"[" + CalendarsConstants.USER_NAME + ":" + User.getUser().getName() + "][" + CalendarsConstants.CORP + ":" + auditory.getCorp() + "][" + CalendarsConstants.NUMBER + ":" + auditory.getNumber() + "]");
			eventValues.put(Events.EVENT_LOCATION, TimeZone.getDefault().getID());
			eventValues.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
			
			long startDate = java.util.Calendar.getInstance().getTimeInMillis();
			long endDate = startDate + EVENT_PERIOD;
			
			eventValues.put("dtstart", startDate);
			eventValues.put("dtend", endDate);
			
			activity.getApplicationContext().getContentResolver().insert(
					Events.CONTENT_URI, eventValues);
		}
	}
}
