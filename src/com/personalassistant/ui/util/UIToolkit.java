package com.personalassistant.ui.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.content.res.Resources;

import com.personalassistant.R;

public class UIToolkit {

	public static String getRomanNumber(int number) {
		return RomanNumerals.convert(number);
	}
	
	public static String formatTime(Calendar calendar) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.UK);
		
		return simpleDateFormat.format(calendar.getTime());
	}
	
	public static String formatDate(Calendar calendar, Resources resources) {
		String time = new String();
		
		time += getDayOfWeekShortCut(calendar.get(Calendar.DAY_OF_WEEK), resources);
		time += ", " + calendar.get(Calendar.DAY_OF_MONTH) + " ";
		time += getMonth(calendar.get(Calendar.MONTH), resources);
		
		return time;
	}
	
	public static String getDayOfWeekShortCut(int dayOfWeek, Resources resources) {
		switch (dayOfWeek) {
			case 1:
				return resources.getString(R.string.sun);
			case 2:
				return resources.getString(R.string.mon);
			case 3:
				return resources.getString(R.string.tue);
			case 4:
				return resources.getString(R.string.wen);
			case 5:
				return resources.getString(R.string.thu);
			case 6:
				return resources.getString(R.string.fri);
			case 7:
				return resources.getString(R.string.sat);
			}
		
		return null;
	}
	
	public static String getMonth(int month, Resources resources) {
		switch (month) {
			case 0:
				return resources.getString(R.string.january_r);
			case 1:
				return resources.getString(R.string.february_r);
			case 2:
				return resources.getString(R.string.march_r);
			case 3:
				return resources.getString(R.string.april_r);
			case 4:
				return resources.getString(R.string.may_r);
			case 5:
				return resources.getString(R.string.june_r);
			case 6:
				return resources.getString(R.string.july_r);
			case 7:
				return resources.getString(R.string.august_r);
			case 8:
				return resources.getString(R.string.september_r);
			case 9:
				return resources.getString(R.string.october_r);
			case 10:
				return resources.getString(R.string.november_r);
			case 11:
				return resources.getString(R.string.december_r);
			}
		
		return null;
	}
}
