package com.personalassistant.ui.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MetroColors {
	private static final int PERIOD = 3;
	
	private static List<String> colors = new ArrayList<String>();
	private static List<String> preColors = new ArrayList<String>();
	
	static {
		colors.add("#00A0B1");
		colors.add("#2E8DEF");
		colors.add("#A700AE");
		colors.add("#643EBF");
		colors.add("#BF1E4B");
		colors.add("#DC572E");
		colors.add("#00A600");
		colors.add("#0A5BC4");
	}

	public static String getRandomColor() {
		if (preColors.size() == PERIOD) {
			preColors.clear();
		}
		
		String color = colors.get(new Random().nextInt(colors.size() - 1));
		while(preColors.contains(color)) {
			color = colors.get(new Random().nextInt(colors.size() - 1));
		}
		
		preColors.add(color);
		
		return color;
	}
}
