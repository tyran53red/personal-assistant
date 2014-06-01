package com.personalassistant.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	private static User user = null;
	
	private UserRole role = UserRole.STUDENT;
	private String name = new String();
	
	private List<UserHandler> handlers = new ArrayList<User.UserHandler>();
	
	public User() {
		
	}
	
	public void addHandler(UserHandler handler) {
		handlers.add(handler);
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
		
		for (UserHandler handler : handlers) {
			handler.onUserRoleChanged();
		}
	}
	
	public interface UserHandler {
		public void onUserRoleChanged();
	}
}
