package com.personalassistant.ui.lecturers;

import android.content.res.Resources;

import com.personalassistant.R;
import com.personalassistant.model.Auditory;
import com.personalassistant.model.Status;
import com.personalassistant.ui.util.MetroColors;
import com.personalassistant.ui.util.UIToolkit;

public class LecturersListItem {
	private String userName = null;
	private Status status = null;
	private Auditory auditory = null;
	private String backgroundColor = null;
	
	private Resources resources = null;
	
	public LecturersListItem(String userName, Resources resources) {
		setUserName(userName);
		this.resources = resources;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Auditory getAuditory() {
		return auditory;
	}

	public void setAuditory(Auditory auditory) {
		this.auditory = auditory;
	}

	public String getBackgroundColor() {
		if (backgroundColor == null) {
			backgroundColor = MetroColors.getRandomColor();
		}
		
		return backgroundColor;
	}

	public String getTopText() {
		return getUserName();
	}

	public String getMiddleText() {
		String text = resources.getString(R.string.lecturer_status_prefix);
		
		if (status != null) {
			text += " " + status.getName();
		} else {
			text += " " + resources.getString(R.string.lecturer_status_empty);
		}
		
		return text;
	}

	public String getBottomText() {
		String text = resources.getString(R.string.lecturer_location_prefix);
		
		if (auditory != null) {
			text += " " + auditory.getNumber() + " " + UIToolkit.getRomanNumber(auditory.getCorp());
		} else {
			text += " " + resources.getString(R.string.lecturer_location_empty);
		}
		
		return text;
	}
}
