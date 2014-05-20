package com.personalassistant.ui.shedule;

import java.util.Calendar;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.personalassistant.App;
import com.personalassistant.R;
import com.personalassistant.model.AbstractLesson;
import com.personalassistant.services.LoadScheduleTask;

public class ScheduleTabContent extends Fragment {
	private ListView listView = null;
	private Calendar calendar = null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.schedule_tab_content, container, false);
    	
		Bundle arguments = getArguments();
		
		int year = arguments.getInt(ScheduleDayItem.YEAR);
		int month = arguments.getInt(ScheduleDayItem.MONTH);
		int day = arguments.getInt(ScheduleDayItem.DAY);
		
		calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		
		listView = (ListView)inflate.findViewById(R.id.schedule_layout);
		load();
        
        return inflate;
    }

	private void load() {
		LoadScheduleTask loadScheduleTask = new LoadScheduleTask(getActivity(), calendar) {
			@Override
			protected void onPreExec() throws Exception {
				
			}
			
			@Override
			protected void onPostExec() throws Exception {
				
			}
			
			@Override
			protected void onDayLoaded(List<AbstractLesson> lessons) {
				ScheduleListItem scheduleListItem[] = new ScheduleListItem[lessons.size()];
				
				int counter = 0;
				for (AbstractLesson lesson : lessons) {
					scheduleListItem[counter++] = new ScheduleListItem(lesson);
				}
				
				ScheduleListItemAdapter scheduleListItemAdapter = new ScheduleListItemAdapter(getActivity(), R.layout.schedule_list_view_row, scheduleListItem);
				listView.setAdapter(scheduleListItemAdapter);
			}
		};
		
		App.getHandler().post(loadScheduleTask);
	}
}
