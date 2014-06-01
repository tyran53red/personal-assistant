package com.personalassistant.model;

public enum LessonType {
	LAB, LEC;
	
	public static LessonType decode(String type) {
		if (type.equals("lab")) {
			return LAB;
		} else {
			return LEC;
		}
	}
	
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
