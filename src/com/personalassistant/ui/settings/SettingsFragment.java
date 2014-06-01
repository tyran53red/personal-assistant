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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.personalassistant.App;
import com.personalassistant.R;
import com.personalassistant.model.Settings;
import com.personalassistant.model.SettingsCalendarItem;
import com.personalassistant.model.User;
import com.personalassistant.model.UserRole;
import com.personalassistant.services.LoadSettingsTask;

public class SettingsFragment extends Fragment {
	public static final int LECTURER = 0;
	public static final int STUDENT = 1;
	
	private Spinner spnSchedule = null;
	private Spinner spnStatus = null;
	private Spinner spnLocation = null;
	
	private RadioGroup radioGroupRole = null;
	
	private ArrayAdapter<SettingsCalendarItem> scheduleAdapter = null;
	private ArrayAdapter<SettingsCalendarItem> statusAdapter = null;
	private ArrayAdapter<SettingsCalendarItem> locationAdapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.settings_fragment, container, false);
		
		spnSchedule = (Spinner)inflate.findViewById(R.id.schedule_list);
		spnStatus = (Spinner)inflate.findViewById(R.id.status_list);
		spnLocation = (Spinner)inflate.findViewById(R.id.location_list);
		radioGroupRole = (RadioGroup)inflate.findViewById(R.id.radioGroupRole);
		
		scheduleAdapter = new ArrayAdapter<SettingsCalendarItem>(getActivity(), android.R.layout.simple_spinner_item);
		scheduleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		statusAdapter = new ArrayAdapter<SettingsCalendarItem>(getActivity(), android.R.layout.simple_spinner_item);
		statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		locationAdapter = new ArrayAdapter<SettingsCalendarItem>(getActivity(), android.R.layout.simple_spinner_item);
		locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spnSchedule.setAdapter(scheduleAdapter);
		spnStatus.setAdapter(statusAdapter);
		spnLocation.setAdapter(locationAdapter);
		
		spnSchedule.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				SharedPreferences preferences = getActivity().getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				
				long value = scheduleAdapter.getItem(position).getId();
				
				editor.putLong(App.SELECTED_SCHEDULE, value);
				editor.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		spnStatus.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				SharedPreferences preferences = getActivity().getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				
				long value = statusAdapter.getItem(position).getId();
				
				editor.putLong(App.SELECTED_STATUS, value);
				editor.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		spnLocation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				SharedPreferences preferences = getActivity().getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				
				long value = locationAdapter.getItem(position).getId();
				
				editor.putLong(App.SELECTED_LOCATION, value);
				editor.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		radioGroupRole.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int indexOfChild = group.indexOfChild(group.findViewById(checkedId));
				
				SharedPreferences preferences = getActivity().getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();

				int value = LECTURER;
				
				if (indexOfChild == LECTURER) {
					User.getUser().setRole(UserRole.LECTURER);
					value = LECTURER;
				} else if (indexOfChild == STUDENT) {
					User.getUser().setRole(UserRole.STUDENT);
					value = STUDENT;
				}
				
				editor.putInt(App.SELECTED_USER_ROLE, value);
				editor.commit();
			}
		});
		
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
				List<SettingsCalendarItem> scheduleItems = settings.getScheduleItems();
				List<SettingsCalendarItem> statusItems = settings.getStatusItems();
				List<SettingsCalendarItem> locationItems = settings.getLocationItems();

				scheduleAdapter.addAll(scheduleItems);
				statusAdapter.addAll(statusItems);
				locationAdapter.addAll(locationItems);
				
				SharedPreferences preferences = getActivity().getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
				
				if (preferences.contains(App.SELECTED_SCHEDULE)) {
					long value = preferences.getLong(App.SELECTED_SCHEDULE, 0);
					
					int counter = 0;
					for (SettingsCalendarItem item : scheduleItems) {
						if (item.getId() == value) {
							spnLocation.setSelection(counter);
							break;
						}
						
						counter++;
					}
				}
				
				if (preferences.contains(App.SELECTED_STATUS)) {
					long value = preferences.getLong(App.SELECTED_STATUS, 0);
					
					int counter = 0;
					for (SettingsCalendarItem item : locationItems) {
						if (item.getId() == value) {
							spnStatus.setSelection(counter);
							break;
						}
						
						counter++;
					}
				}
				
				if (preferences.contains(App.SELECTED_LOCATION)) {
					long value = preferences.getLong(App.SELECTED_LOCATION, 0);
					
					int counter = 0;
					for (SettingsCalendarItem item : locationItems) {
						if (item.getId() == value) {
							spnLocation.setSelection(counter);
							break;
						}
						
						counter++;
					}
				}
				
				if (preferences.contains(App.SELECTED_USER_ROLE)) {
					int i = preferences.getInt(App.SELECTED_USER_ROLE, 0);
					if (i == LECTURER) {
						radioGroupRole.check(R.id.lecturer);
					} else if (i == STUDENT) {
						radioGroupRole.check(R.id.student);
					}
				}
			}

			@Override
			protected void onPostExec() throws IOException {
				
			}
		});
	}
}
