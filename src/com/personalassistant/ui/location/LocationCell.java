package com.personalassistant.ui.location;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.personalassistant.R;
import com.personalassistant.model.Auditory;
import com.personalassistant.ui.util.MetroColors;
import com.personalassistant.ui.util.UIToolkit;

public class LocationCell {
	private Auditory auditory = null;
	private View cell = null;
	
	public LocationCell(Auditory auditory, LayoutInflater layoutInflater) {
		this.auditory = auditory;
		
		cell = layoutInflater.inflate(R.layout.location_cell, null);
		
		load();
	}
	
	private void load() {
		TextView txtTop = (TextView)cell.findViewById(R.id.location_cell_search);
		txtTop.setText(auditory.getNumber() + UIToolkit.getRomanNumber(auditory.getCorp()));
		TextView txtMiddle = (TextView)cell.findViewById(R.id.location_cell_middle);
		txtMiddle.setText("auditory");
		
		cell.setBackgroundColor(Color.parseColor(MetroColors.getRandomColor()));
	}

	public View getCellView() {
		return cell;
	}
}
