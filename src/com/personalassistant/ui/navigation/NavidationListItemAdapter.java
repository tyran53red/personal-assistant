package com.personalassistant.ui.navigation;

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

public class NavidationListItemAdapter extends ArrayAdapter<NavigationMenuItem> {
	private Context context; 
	private int layoutResourceId;    
	private NavigationMenuItem data[] = null;
	
	public NavidationListItemAdapter(Context context, int layoutResourceId, NavigationMenuItem[] data) {
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
            holder.globalMenuRow = (LinearLayout)row.findViewById(R.id.globalMenuRow);
            holder.txtGlobalRow = (TextView)row.findViewById(R.id.txtGlobalRow);
            holder.imgGlobalRow = (TextView)row.findViewById(R.id.imgGlobalRow);
            
            row.setTag(holder);
        } else {
            holder = (Holder)row.getTag();
        }
        
        NavigationMenuItem item = data[position];
        holder.txtGlobalRow.setText(item.getName());
        holder.imgGlobalRow.setCompoundDrawablesWithIntrinsicBounds(item.getIcon(), 0, 0, 0);
        
        if (position == getSelectedPosition()) {
        	holder.globalMenuRow.setBackgroundColor(Color.WHITE);
        } else {
        	holder.globalMenuRow.setBackgroundColor(Color.TRANSPARENT);
        }
        
        return row;
    }
	
	protected int getSelectedPosition() {
		return 0;
	}
	
	public NavigationMenuItem getItem(int position) {
		return data[position];
	}
	
	private class Holder {
		private LinearLayout globalMenuRow;
        private TextView txtGlobalRow;
        private TextView imgGlobalRow;
    }
}
