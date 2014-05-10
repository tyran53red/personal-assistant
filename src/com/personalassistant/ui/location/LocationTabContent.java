package com.personalassistant.ui.location;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.personalassistant.R;
import com.personalassistant.model.Auditory;

public class LocationTabContent extends Fragment {
	private TableLayout layout = null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.location_tab_content, container, false);
    	
    	layout = (TableLayout)inflate.findViewById(R.id.location_layout);
		load();
        
        return inflate;
    }

	private void load() {
		List<Auditory> auditories = new ArrayList<Auditory>();
		auditories.add(new Auditory(318, 4));
		auditories.add(new Auditory(319, 4));
		auditories.add(new Auditory(320, 4));
		auditories.add(new Auditory(321, 4));
		auditories.add(new Auditory(325, 4));
		auditories.add(new Auditory(318, 4));
		auditories.add(new Auditory(319, 4));
		auditories.add(new Auditory(320, 4));
		auditories.add(new Auditory(321, 4));
		auditories.add(new Auditory(325, 4));
		auditories.add(new Auditory(318, 4));
		auditories.add(new Auditory(319, 4));
		auditories.add(new Auditory(320, 4));
		auditories.add(new Auditory(321, 4));
		auditories.add(new Auditory(325, 4));
		
		createTable(layout, auditories);
	}
	
	private void createTable(TableLayout layout, List<Auditory> auditories) {
		LayoutInflater layoutInflater = getActivity().getLayoutInflater();
		
		for (int i = 0; i < auditories.size(); i += 2) {
			TableRow row = (TableRow)layoutInflater.inflate(R.layout.location_row, null);

			TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
			tableRowParams.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.location_tab_content_space));

			row.setLayoutParams(tableRowParams);
			
			layout.addView(row);
			
			LocationCell locationCell1 = new LocationCell(auditories.get(i), layoutInflater);
			row.addView(locationCell1.getCellView());

			
			if (auditories.size() > i + 1) {
				LocationCell locationCell2 = new LocationCell(auditories.get(i + 1), layoutInflater);
				row.addView(locationCell2.getCellView());
			} else {
				LocationCellSearch cellSearch = new LocationCellSearch(layoutInflater);
				row.addView(cellSearch.getSearchCellView());
			}
		}
	}
}
