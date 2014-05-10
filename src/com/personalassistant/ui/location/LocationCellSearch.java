package com.personalassistant.ui.location;

import com.personalassistant.R;
import com.personalassistant.ui.util.MetroColors;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

public class LocationCellSearch implements OnClickListener {
	private View cell = null;
	
	public LocationCellSearch(LayoutInflater layoutInflater) {
		cell = layoutInflater.inflate(R.layout.location_cell_search, null);
		cell.setOnClickListener(this);
		cell.setBackgroundColor(Color.parseColor(MetroColors.getRandomColor()));
	}

	@Override
	public void onClick(View v) {
		// TODO Search
	}
	
	public View getSearchCellView() {
		return cell;
	}
}
