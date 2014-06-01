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
import com.personalassistant.model.LessonImpl;
import com.personalassistant.model.LessonType;
import com.personalassistant.ui.util.DataToolkit;

public abstract class LoadScheduleTask extends CalendarAsyncTask {
	public static final String[] EVENT_PROJECTION = new String[] {
		Events._ID,
		Events.DTSTART,
		Events.DTEND,
		Events.RRULE,
		Events.TITLE,
		Events.DESCRIPTION
	};
	
	public static final int TITLE = 4;
	public static final int DESCRIPTION = 5;
	
	public static final String ORDER = Events.DTSTART;
//	public static final String WHERE = "((" + Events.CALENDAR_ID + " = ?) AND (" + Events.DTSTART + " >= ?) AND (" + Events.DTEND + " <= ?))";
	public static final String WHERE = "((" + Events.CALENDAR_ID + " = ?))";

	private Calendar calendarStart = null;
	private Calendar calendarEnd = null;
	private Activity activity = null;
	  
	public LoadScheduleTask(Activity activity, Calendar calendar) {
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
		if (preferences.contains(App.SELECTED_SCHEDULE)) {
			long value = preferences.getLong(App.SELECTED_SCHEDULE, 0);
		
			ContentResolver contentResolver = activity.getContentResolver();
			
			Cursor cursor = contentResolver.query(Events.CONTENT_URI,
					EVENT_PROJECTION,
					WHERE,
					new String[] {
						String.valueOf(value),
					},
					ORDER);
			
			
			List<LessonImpl> lessons = new ArrayList<LessonImpl>();
			while(cursor.moveToNext()) {
				String description = cursor.getString(DESCRIPTION);
				Map<String, String> attributes = DataToolkit.getEventAttributes(description);

				if (attributes.containsKey(CalendarsConstants.MIDDLE) && attributes.containsKey(CalendarsConstants.CORP)
					&& attributes.containsKey(CalendarsConstants.NUMBER) && attributes.containsKey(CalendarsConstants.TYPE)) {
					LessonImpl lesson = new LessonImpl();
					
					lesson.setName(cursor.getString(TITLE));
					lesson.setCorp(Integer.parseInt(attributes.get(CalendarsConstants.CORP)));
					lesson.setAuditory(Integer.parseInt(attributes.get(CalendarsConstants.NUMBER)));
					lesson.setLessonType(LessonType.decode(attributes.get(CalendarsConstants.TYPE)));
					lesson.setMiddle(attributes.get(CalendarsConstants.MIDDLE));
					
					lessons.add(lesson);
				}
			}
		
			onDayLoaded(lessons);
		}
	}

	protected abstract void onDayLoaded(List<LessonImpl> lessons);
}
