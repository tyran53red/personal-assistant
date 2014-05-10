package com.personalassistant.model;

public class Auditory {
	private int number = -1;
	private int corp = -1;
	
	public Auditory(int number, int corp) {
		setNumber(number);
		setCorp(corp);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getCorp() {
		return corp;
	}

	public void setCorp(int corp) {
		this.corp = corp;
	}

}
