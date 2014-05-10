package com.personalassistant.ui.main;

import java.util.Calendar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.personalassistant.R;
import com.personalassistant.model.AbstractLesson;
import com.personalassistant.model.LessonType;
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
		
		sheduleListView = (ListView)inflate.findViewById(R.id.main_fragment_list_view);
		
		MainListItemAdapter adapter = new MainListItemAdapter(getActivity(), R.layout.main_list_view_row, loadListItem());
		sheduleListView.setAdapter(adapter);
		
		return inflate;
	}
	
	private void setClockTime(Calendar calendar) {
		twClock.setText(UIToolkit.formatTime(calendar));
		twDate.setText(UIToolkit.formatDate(calendar, getResources()));
	}
	
	private MainListItem[] loadListItem() {
		MainListItem[] result = new MainListItem[] {
			new MainListItem(new AbstractLesson("Основи програмування", LessonType.LAB, 4, 125)),
			new MainListItem(new AbstractLesson("Геометричне моделювання", LessonType.LAB, 4, 322)),
			new MainListItem(new AbstractLesson("Методи побудови математичної моделі", LessonType.LEC, 4, 501)),
			new MainListItem(new AbstractLesson("Основи програмування", LessonType.LAB, 4, 125)),
			new MainListItem(new AbstractLesson("Геометричне моделювання", LessonType.LAB, 4, 322)),
			new MainListItem(new AbstractLesson("Методи побудови математичної моделі", LessonType.LEC, 4, 501)),
		};
		
		return result;
	}
}
