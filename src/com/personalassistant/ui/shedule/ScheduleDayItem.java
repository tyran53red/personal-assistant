package com.personalassistant.ui.shedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;

import com.personalassistant.model.AbstractLesson;
import com.personalassistant.ui.util.UIToolkit;

public class ScheduleDayItem {
	private Calendar day = null;
	private List<AbstractLesson> lessons = new ArrayList<AbstractLesson>();
	
	public ScheduleDayItem(Calendar day) {
		this.day = day;
	}

	public List<AbstractLesson> getLessons() {
		return lessons;
	}
	
	public Fragment getFragment() {
		ScheduleTabContent tabContent = new ScheduleTabContent();

	    Bundle args = new Bundle();
	    args.putString("calendar", day.toString());
	    tabContent.setArguments(args);

	    return tabContent;
	}

	public String getTitle(Resources resources) {
		return UIToolkit.formatDate(day, resources);
	}
}
