package com.personalassistant.model;

public class Status {
	private String identifier = null;
	private String name = null;
	
	public Status(String identifier, String name) {
		setIdentifier(identifier);
		setName(name);
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}
}
