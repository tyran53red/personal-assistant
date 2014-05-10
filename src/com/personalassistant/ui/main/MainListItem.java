package com.personalassistant.ui.main;

import com.personalassistant.model.Lesson;
import com.personalassistant.ui.util.MetroColors;
import com.personalassistant.ui.util.UIToolkit;

public class MainListItem {
	private Lesson lesson = null;
	private String backgroundColor = null;
	
	public MainListItem(Lesson lesson) {
		this.lesson = lesson;
	}
	
	public Lesson getLesson() {
		return lesson;
	}
	
	public String getTopText() {
		return lesson.getName();
	}
	
	public String getMiddleText() {
		return "Middle";
	}
	
	public String getBottomText() {
		return UIToolkit.getRomanNumber(lesson.getCorp()) + " н.к. " + lesson.getAuditory() + ", " + lesson.getType().toString();
	}
	
	public String getBackgroundColor() {
		if (backgroundColor == null) {
			backgroundColor = MetroColors.getRandomColor();
		}
		
		return backgroundColor;
	}
}
