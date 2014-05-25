package com.personalassistant.services;

import java.util.TimeZone;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.CalendarContract.Events;

import com.personalassistant.App;
import com.personalassistant.model.Status;
import com.personalassistant.model.User;

public abstract class CreateStatusEvent extends CalendarAsyncTask {
	private Activity activity = null;
	private Status status = null;
	
	private static long EVENT_PERIOD = 1000 * 60 * 60;
	
	public CreateStatusEvent(Activity activity, Status status) {
		this.activity = activity;
		this.status = status;
	}

	@Override
	protected void doInBackground() throws Exception {
		SharedPreferences preferences = activity.getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		if (preferences.contains(App.SELECTED_STATUS)) {
			long value = preferences.getLong(App.SELECTED_STATUS, 0);
			
			ContentValues eventValues = new ContentValues();
			
			eventValues.put(Events.CALENDAR_ID, value);
			eventValues.put(Events.TITLE, User.getUser().getName());
			eventValues.put(Events.DESCRIPTION,
					"[user-name:" + User.getUser().getName() + "][status:" + status.getIdentifier() + "]");
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
