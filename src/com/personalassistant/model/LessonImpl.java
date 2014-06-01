package com.personalassistant.model;

public class LessonImpl implements Lesson {
	private String name = null;
	private LessonType type = null;
	private int corp = -1;
	private int auditory = -1;
	private String middle = null;
	
	public LessonImpl() {
		
	}
	
	public LessonImpl(String name, LessonType type, int corp, int auditory, String middle) {
		setName(name);
		setLessonType(type);
		setCorp(corp);
		setAuditory(auditory);
		setMiddle(middle);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public LessonType getType() {
		return type;
	}

	@Override
	public void setLessonType(LessonType type) {
		this.type = type;
	}

	@Override
	public int getCorp() {
		return corp;
	}

	@Override
	public void setCorp(int corp) {
		this.corp = corp;
	}

	@Override
	public int getAuditory() {
		return auditory;
	}

	@Override
	public void setAuditory(int auditory) {
		this.auditory = auditory;
	}

	@Override
	public String getMiddle() {
		return middle;
	}

	@Override
	public void setMiddle(String middle) {
		this.middle = middle;
	}
}
