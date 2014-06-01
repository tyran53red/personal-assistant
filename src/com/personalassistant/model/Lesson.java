package com.personalassistant.model;

public interface Lesson {
	public String getName();
	public void setName(String name);
	public LessonType getType();
	public void setLessonType(LessonType type);
	public int getCorp();
	public void setCorp(int corp);
	public int getAuditory();
	public void setAuditory(int auditory);
	public String getMiddle();
	public void setMiddle(String middle);
}
