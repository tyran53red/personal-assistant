package com.personalassistant.ui.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.personalassistant.R;

public class MainListItemAdapter extends ArrayAdapter<MainListItem> {
	private Context context; 
	private int layoutResourceId;    
	private MainListItem data[] = null;
	
	public MainListItemAdapter(Context context, int layoutResourceId, MainListItem[] data) {
		super(context, layoutResourceId, data);
		
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
        Holder holder = null;
        
        if(row == null) {
        	LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new Holder();
            holder.layout = (LinearLayout)row.findViewById(R.id.main_list_layout);
            holder.txtTop = (TextView)row.findViewById(R.id.txtTop);
            holder.txtMiddle = (TextView)row.findViewById(R.id.txtMiddle);
            holder.txtBottom = (TextView)row.findViewById(R.id.txtBottom);
            
            row.setTag(holder);
        } else {
            holder = (Holder)row.getTag();
        }
        
        MainListItem item = data[position];
        holder.layout.setBackgroundColor(Color.parseColor(item.getBackgroundColor()));
        holder.txtTop.setText(item.getTopText());
        holder.txtMiddle.setText(item.getMiddleText());
        holder.txtBottom.setText(item.getBottomText());
        
        return row;
	}

	private class Holder {
		private LinearLayout layout;
		private TextView txtTop;
        private TextView txtMiddle;
        private TextView txtBottom;
    }
}
