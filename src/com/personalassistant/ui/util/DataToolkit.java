package com.personalassistant.ui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataToolkit {

	public static Map<String, String> getEventAttributes(String text) {
		Map<String, String> result = new HashMap<String, String>();
		
		String[] split = split(text, '[');
		for (String s : split) {
			int indexOf = s.indexOf(':');
			String key = s.substring(0, indexOf);
			String value = s.substring(indexOf + 1, s.length() - 1);
			
			result.put(key, value);
		}
		
		return result;
	}
	
	public static String[] split(String string, char pattern) {
		List<String> result = new ArrayList<String>();
		
		String tempString = new String();
		for (char c : string.toCharArray()) {
			if (c == pattern) {
				if (tempString.length() > 0) {
					result.add(new String(tempString));
				}
				
				tempString = new String();
			} else {
				tempString += c;
			}
		}
		
		if (tempString.length() > 0) {
			result.add(new String(tempString));
		}
		
		return result.toArray(new String[0]);
	}

}
