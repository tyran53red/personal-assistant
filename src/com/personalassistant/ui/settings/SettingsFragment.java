package com.personalassistant.ui.settings;

import java.io.IOException;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.personalassistant.App;
import com.personalassistant.R;
import com.personalassistant.model.Settings;
import com.personalassistant.model.SettingsScheduleItem;
import com.personalassistant.services.LoadSettingsTask;

public class SettingsFragment extends Fragment implements OnItemSelectedListener {
	private Spinner spinner = null;
	private ArrayAdapter<SettingsScheduleItem> arrayAdapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.settings_fragment, container, false);
		
		spinner = (Spinner)inflate.findViewById(R.id.schedule_list);
		
		arrayAdapter = new ArrayAdapter<SettingsScheduleItem>(getActivity(), android.R.layout.simple_spinner_item);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(arrayAdapter);
		
		spinner.setOnItemSelectedListener(this);
		
		load();
		
		return inflate;
	}
	
	private void load() {
		App.getHandler().post(new LoadSettingsTask(getActivity()) {
			
			@Override
			protected void onPreExec() throws IOException {
				
			}
			
			@Override
			protected void onDataReady(Settings settings) throws Exception {
				List<SettingsScheduleItem> scheduleItems = settings.getScheduleItems();
				arrayAdapter.addAll(scheduleItems);
				
				SharedPreferences preferences = getActivity().getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
				if (preferences.contains(App.SELECTED_SCHEDULE)) {
					long value = preferences.getLong(App.SELECTED_SCHEDULE, 0);
					
					int counter = 0;
					for (SettingsScheduleItem item : scheduleItems) {
						if (item.getId() == value) {
							spinner.setSelection(counter);
							break;
						}
						
						counter++;
					}
					
				}
			}

			@Override
			protected void onPostExec() throws IOException {
				
			}
		});
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		SharedPreferences preferences = getActivity().getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		
		long value = arrayAdapter.getItem(position).getId();
		
		editor.putLong(App.SELECTED_SCHEDULE, value);
		editor.commit();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
}
