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
import com.personalassistant.model.AbstractLesson;
import com.personalassistant.model.LessonType;

public abstract class LoadScheduleTask extends CalendarAsyncTask {
	public static final String[] EVENT_PROJECTION = new String[] {
		Events._ID,
		Events.DTSTART,
		Events.DTEND,
		Events.RRULE,
		Events.TITLE
	};
	
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
						// String.valueOf(calendarStart.getTimeInMillis()),
						// String.valueOf(calendarEnd.getTimeInMillis())
					},
					ORDER);
			
			
			List<AbstractLesson> lessons = new ArrayList<AbstractLesson>();
			while(cursor.moveToNext()) {
				AbstractLesson lesson = new AbstractLesson();
				lesson.setName(cursor.getString(4));
			/*	
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(cursor.getLong(1));

				String date = "" + calendarStart.get(Calendar.DAY_OF_MONTH) + "-" + calendarStart.get(Calendar.MONTH) + "-" + calendarStart.get(Calendar.YEAR) + "";
				String date2 = "" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR) + "";
				*/
				lesson.setLessonType(LessonType.LAB);
				
				lessons.add(lesson);
			}
		
			onDayLoaded(lessons);
		}
	}

	protected abstract void onDayLoaded(List<AbstractLesson> lessons);
}
