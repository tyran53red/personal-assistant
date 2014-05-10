package com.personalassistant.ui.shedule;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.personalassistant.R;
import com.personalassistant.model.AbstractLesson;
import com.personalassistant.model.LessonType;

public class ScheduleTabContent extends Fragment {
	private ListView listView = null;
	// private Calendar day = null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.schedule_tab_content, container, false);
    	
		// day = Calendar.getInstance().
		listView = (ListView)inflate.findViewById(R.id.schedule_layout);
		load();
        
        return inflate;
    }

	private void load() {
		ScheduleListItemAdapter scheduleListItemAdapter = new ScheduleListItemAdapter(getActivity(), R.layout.schedule_list_view_row, loadListItems());
		listView.setAdapter(scheduleListItemAdapter);
		
	}
	
	private ScheduleListItem[] loadListItems() {
		ScheduleListItem[] items = new ScheduleListItem[] {
				new ScheduleListItem(new AbstractLesson("Основи програмування", LessonType.LAB, 4, 125)),
				new ScheduleListItem(new AbstractLesson("Геометричне моделювання", LessonType.LAB, 4, 322)),
				new ScheduleListItem(new AbstractLesson("Методи побудови математичної моделі", LessonType.LEC, 4, 501)),
				new ScheduleListItem(new AbstractLesson("Основи програмування", LessonType.LAB, 4, 125)),
				new ScheduleListItem(new AbstractLesson("Геометричне моделювання", LessonType.LAB, 4, 322)),
				new ScheduleListItem(new AbstractLesson("Методи побудови математичної моделі", LessonType.LEC, 4, 501)),	
				new ScheduleListItem(new AbstractLesson("Методи побудови математичної моделі", LessonType.LEC, 4, 501)),
				new ScheduleListItem(new AbstractLesson("Основи програмування", LessonType.LAB, 4, 125)),
				new ScheduleListItem(new AbstractLesson("Геометричне моделювання", LessonType.LAB, 4, 322)),
				new ScheduleListItem(new AbstractLesson("Методи побудови математичної моделі", LessonType.LEC, 4, 501)),	
		};
		
		return items;
	}
}
