package com.personalassistant.model;

public enum LessonType {
	LAB, LEC;
	
	@Override
	public String toString() {
		switch (this) {
			case LAB:
				return "лабораторна";
			default:
				return "лекція";
			}
	}
}
