package com.personalassistant.services;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract.Calendars;

import com.personalassistant.App;
import com.personalassistant.App.CalendarType;
import com.personalassistant.model.Settings;
import com.personalassistant.model.SettingsScheduleItem;

public abstract class LoadSettingsTask extends CalendarAsyncTask {
	public static final String[] EVENT_PROJECTION = new String[] {
	    Calendars._ID,
	    Calendars.CALENDAR_DISPLAY_NAME,
	};
	  
	private static final int PROJECTION_ID_INDEX = 0;
	private static final int PROJECTION_DISPLAY_NAME_INDEX = 1;
	
	private Activity activity = null;
	
	public LoadSettingsTask(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected void doInBackground() throws Exception {
		ContentResolver contentResolver = activity.getContentResolver();
		
		Uri uri = Calendars.CONTENT_URI;   
		String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND (" + Calendars.ACCOUNT_TYPE + " = ?))";
		
		String[] selectionArgs = new String[] { App.getCredential().getSelectedAccountName(), "com.google", }; 
		Cursor cur = contentResolver.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
		
		List<SettingsScheduleItem> scheduleItems = new ArrayList<SettingsScheduleItem>();
		
		while (cur.moveToNext()) {
		    long calID = -1;
		    String displayName = null;
		    
		    calID = cur.getLong(PROJECTION_ID_INDEX);
		    displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
		    
		    if (calID != -1 && displayName != null &&
		    	App.CalendarType.checkCalendarType(displayName) == CalendarType.SCHEDULE) {
		    	scheduleItems.add(new SettingsScheduleItem(displayName, calID));
		    }
		}
		
		Settings settings = new Settings();
		settings.setScheduleItems(scheduleItems);
		
		onDataReady(settings);
	}
	
	protected abstract void onDataReady(Settings settings) throws Exception;
}
