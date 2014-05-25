package com.personalassistant.ui.lecturers;

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
import com.personalassistant.services.LoadLecturersTask;

public class LecturersFragment extends Fragment {
	private ListView lecturersListView;
	private LecturersListItemAdapter lecturersListItemAdapter = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.lecturers_fragment, container, false);
		
		lecturersListItemAdapter = new LecturersListItemAdapter(getActivity(), R.layout.lecturer_list_view_row);
		
		lecturersListView = (ListView)inflate.findViewById(R.id.lecturers_fragment_list_view);
		lecturersListView.setAdapter(lecturersListItemAdapter);
		
		load();
		
		return inflate;
	}
	
	private void load() {
		App.getHandler().post(new LoadLecturersTask(getActivity(), Calendar.getInstance()) {
			
			@Override
			protected void onPreExec() throws Exception {
				
			}
			
			@Override
			protected void onPostExec() throws Exception {
				
			}
			
			@Override
			protected void onDayLoaded(List<LecturersListItem> data) {
				lecturersListItemAdapter.addAll(data);
			}
		});
	}
}
