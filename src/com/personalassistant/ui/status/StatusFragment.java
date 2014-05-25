package com.personalassistant.ui.status;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.personalassistant.App;
import com.personalassistant.R;
import com.personalassistant.model.Status;
import com.personalassistant.services.CreateStatusEvent;

public class StatusFragment extends Fragment {
	private List<Status> items = null;
	private RadioGroup statusGroup = null;
	private LayoutInflater inflater = null;
	private Button btnAccept = null;

	private int selectedPosition = -1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.status_fragment, container, false);
		
		items = new ArrayList<Status>();
		statusGroup = (RadioGroup)inflate.findViewById(R.id.radioGroupStatus);
		this.inflater = inflater;
		btnAccept = (Button)inflate.findViewById(R.id.buttonAccept);
		
		statusGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				selectedPosition = statusGroup.indexOfChild(statusGroup.findViewById(checkedId));
			}
		});
		
		btnAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (selectedPosition != -1) {
					App.getHandler().post(new CreateStatusEvent(getActivity(), items.get(selectedPosition)) {
						@Override
						protected void onPreExec() throws Exception {
							
						}
						
						@Override
						protected void onPostExec() throws Exception {
							
						}
					});
				}
			}
		});
		
		load();
		
		return inflate;
	}
	
	private void load() {
		for (Status status : App.getDefaultStatuses(getActivity())) {
			statusGroup.addView(createRadioButton(status));
		}
	}
	
	private View createRadioButton(Status item) {
		items.add(item);
		
		RadioButton radioButton = (RadioButton)inflater.inflate(R.layout.status_radio_button, null, false);
		radioButton.setText(item.getName());
		
		return radioButton;
	}
}
