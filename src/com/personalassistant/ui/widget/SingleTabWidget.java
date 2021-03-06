package com.personalassistant.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.TextView;

public class SingleTabWidget extends TabWidget {

	private OnTabChangedListener mOnTabChangedListener;

	private int mLayoutId;
	private int mSelectedTab;

	public static interface OnTabChangedListener {
		public void onTabChanged(int tabIndex);
	}

	public SingleTabWidget(Context context, AttributeSet attrs) {
		super(context, attrs);

		setStripEnabled(false);
		setDividerDrawable(null);
	}

	public void setLayout(int layoutResId) {
		mLayoutId = layoutResId;
	}

	public void addTab(String title, int index) {
		addTab(0, title, index);
	}

	public void addTab(int imageResId, String title, int index) {
		View view = LayoutInflater.from(getContext()).inflate(mLayoutId, this, false);
		if (view == null) {
			throw new RuntimeException("You must call 'setLayout(int layoutResId)' to initialize the tab.");
		} else {
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
			layoutParams.width = 0;
			layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
			layoutParams.weight = 1.0f;
			view.setLayoutParams(layoutParams);
		}

		if (view instanceof TextView) {
			if (imageResId > 0) {
				((TextView) view).setCompoundDrawablesWithIntrinsicBounds(0, imageResId, 0, 0);
			}
			if (!TextUtils.isEmpty(title)) {
				((TextView) view).setText(title);
			}
		} else if (view instanceof ImageView) {
			if (imageResId > 0) {
				((ImageView) view).setImageResource(imageResId);
			}
		} else {
			TextView textView = (TextView) view.findViewById(android.R.id.title);
			if (textView == null) {
				throw new RuntimeException("Your layout must have a TextView whose id attribute is 'android.R.id.title'");
			} else {
				textView.setText(title);
			}
			ImageView imageView = (ImageView) view.findViewById(android.R.id.icon);
			if (imageView == null) {
				throw new RuntimeException("Your layout must have a ImageView whose id attribute is 'android.R.id.icon'");
			} else {
				imageView.setImageResource(imageResId);
			}
		}

		addView(view, index);
		view.setOnClickListener(new TabClickListener());
		view.setOnFocusChangeListener(this);
	}

	public void setOnTabChangedListener(OnTabChangedListener listener) {
		mOnTabChangedListener = listener;
	}

	@Override
	public void onFocusChange(View view, boolean hasFocus) {
	}
	
	@Override
	public void setCurrentTab(int index) {
		mSelectedTab = index;
		
		super.setCurrentTab(index);
	}

	private class TabClickListener implements OnClickListener {
		public TabClickListener() {
		}

		@Override
		public void onClick(View view) {
			for (int i = 0; i < getTabCount(); i++) {
				if (getChildAt(i).equals(view)) {
					if (mOnTabChangedListener != null && i != mSelectedTab) {
						mSelectedTab = i;
						setCurrentTab(i);
						mOnTabChangedListener.onTabChanged(i);
					}
					
					break;
				}
			}
		}
	}
}
