package com.personalassistant.model;

public enum UserRole {
	STUDENT(0), LECTURER(1);
	
	private int value = -1;
	
	private UserRole(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
