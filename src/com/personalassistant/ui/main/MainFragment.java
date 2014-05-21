package com.personalassistant.ui.main;

import java.util.Calendar;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.personalassistant.App;
import com.personalassistant.R;
import com.personalassistant.model.AbstractLesson;
import com.personalassistant.services.LoadScheduleTask;
import com.personalassistant.ui.util.UIToolkit;

public class MainFragment extends Fragment {
	private TextView twClock;
	private TextView twDate;
	
	private ListView sheduleListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.main_fragment, container, false);
		twClock = (TextView)inflate.findViewById(R.id.main_clock);
		twDate = (TextView)inflate.findViewById(R.id.main_date);
		
		setClockTime(Calendar.getInstance());
		
		/*App.getHandler().post(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						setClockTime(Calendar.getInstance());
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});*/
		
		sheduleListView = (ListView)inflate.findViewById(R.id.main_fragment_list_view);
		
		load();
		
		return inflate;
	}
	
	private void setClockTime(Calendar calendar) {
		twClock.setText(UIToolkit.formatTime(calendar));
		twDate.setText(UIToolkit.formatDate(calendar, getResources()));
	}
	
	private void load() {
		LoadScheduleTask loadScheduleTask = new LoadScheduleTask(getActivity(), Calendar.getInstance()) {
			@Override
			protected void onPreExec() throws Exception {
				
			}
			
			@Override
			protected void onPostExec() throws Exception {
				
			}
			
			@Override
			protected void onDayLoaded(List<AbstractLesson> lessons) {
				MainListItem mainListItem[] = new MainListItem[lessons.size()];
				
				int counter = 0;
				for (AbstractLesson lesson : lessons) {
					mainListItem[counter++] = new MainListItem(lesson);
				}
				
				MainListItemAdapter adapter = new MainListItemAdapter(getActivity(), R.layout.main_list_view_row, mainListItem);
				sheduleListView.setAdapter(adapter);
			}
		};
		
		App.getHandler().post(loadScheduleTask);
	}
}
