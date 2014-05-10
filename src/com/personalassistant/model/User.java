package com.personalassistant.model;

public class User {
	private static User user = null;
	
	private UserRole role = UserRole.LECTURER;
	private String name = "Андрій Гурський";
	
	public User() {
		
	}
	
	public static User getUser() {
		if (user == null) {
			user = new User();
		}
		
		return user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
}
