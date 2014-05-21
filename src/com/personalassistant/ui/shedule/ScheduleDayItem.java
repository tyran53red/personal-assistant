package com.personalassistant.ui.shedule;

import java.util.Calendar;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;

import com.personalassistant.ui.util.UIToolkit;

public class ScheduleDayItem {
	public static String YEAR = "year";
	public static String MONTH = "month";
	public static String DAY = "day";
	
	private Calendar day = null;
	
	public ScheduleDayItem(Calendar day) {
		this.day = day;
	}
	
	public Fragment getFragment() {
		ScheduleTabContent tabContent = new ScheduleTabContent();

	    Bundle args = new Bundle();
	    args.putInt(YEAR, day.get(Calendar.YEAR));
	    args.putInt(MONTH, day.get(Calendar.MONTH));
	    args.putInt(DAY, day.get(Calendar.DAY_OF_MONTH));
	    tabContent.setArguments(args);

	    return tabContent;
	}

	public String getTitle(Resources resources) {
		return UIToolkit.formatDate(day, resources);
	}
	
	public Calendar getCalendar() {
		return day;
	}
}
