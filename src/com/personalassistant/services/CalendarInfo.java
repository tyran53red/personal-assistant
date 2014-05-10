package com.personalassistant.services;

import com.google.api.client.util.Objects;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;

public class CalendarInfo implements Comparable<CalendarInfo>, Cloneable {
	public static final String FIELDS = "id,summary";
	public static final String FEED_FIELDS = "items(" + FIELDS + ")";

	private String id;
	private String summary;

	public CalendarInfo(String id, String summary) {
		this.id = id;
		this.summary = summary;
	}

	public CalendarInfo(Calendar calendar) {
		update(calendar);
	}

	public CalendarInfo(CalendarListEntry calendar) {
		update(calendar);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(CalendarInfo.class).add("id", id)
				.add("summary", summary).toString();
	}

	public int compareTo(CalendarInfo other) {
		return summary.compareTo(other.summary);
	}

	@Override
	public CalendarInfo clone() {
		try {
			return (CalendarInfo) super.clone();
		} catch (CloneNotSupportedException exception) {
			// should not happen
			throw new RuntimeException(exception);
		}
	}

	public void update(Calendar calendar) {
		id = calendar.getId();
		summary = calendar.getSummary();
	}

	public void update(CalendarListEntry calendar) {
		id = calendar.getId();
		summary = calendar.getSummary();
	}
}
