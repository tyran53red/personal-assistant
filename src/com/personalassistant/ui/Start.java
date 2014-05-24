package com.personalassistant.ui;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.calendar.CalendarScopes;
import com.personalassistant.App;
import com.personalassistant.R;
import com.personalassistant.model.Settings;
import com.personalassistant.model.SettingsCalendarItem;
import com.personalassistant.model.User;
import com.personalassistant.services.LoadSettingsTask;

public class Start extends Activity {
	private static final String PREF_ACCOUNT_NAME = "accountName";
	
	private static final int REQUEST_ACCOUNT_PICKER = 2;
	private GoogleAccountCredential credential = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		setupSettings();
	}
	
	private void load() {
		App.getHandler().post(new LoadSettingsTask(this) {
			@Override
			protected void onPreExec() throws IOException {
				
			}
			
			@Override
			protected void onDataReady(Settings settings) throws Exception {
				List<SettingsCalendarItem> scheduleItems = settings.getScheduleItems();
				List<SettingsCalendarItem> locationItems = settings.getLocationItems();
				
				SharedPreferences preferences = getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				
				if (!preferences.contains(App.SELECTED_SCHEDULE) && scheduleItems.size() > 0) {
					editor.putLong(App.SELECTED_LOCATION, scheduleItems.get(0).getId());
				}
				
				if (!preferences.contains(App.SELECTED_LOCATION) && locationItems.size() > 0) {
					editor.putLong(App.SELECTED_LOCATION, locationItems.get(0).getId());
				}

				editor.commit();
			}

			@Override
			protected void onPostExec() throws IOException {
				startAppContent();
			}
		});
	}
	
	private  void startAppContent() {
		App.setCredential(credential);
		
		Intent intent = new Intent(this, Main.class);
		startActivity(intent);
		finish();
	}
	
	private void setupSettings() {
		if (credential == null) {
			credential = GoogleAccountCredential.usingOAuth2(this, Collections.singleton(CalendarScopes.CALENDAR));
		}

		SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
		String accountName = settings.getString(PREF_ACCOUNT_NAME, null);
		
		if (accountName != null) {
			credential.setSelectedAccountName(accountName);
			User.getUser().setName(accountName);
			startAppContent();
		} else if (checkGooglePlayServicesAvailable()) {
			haveGooglePlayServices();
		} else {
			// TODO Error...
		}
	}
	
	private boolean checkGooglePlayServicesAvailable() {
		final int connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		
		if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
			return false;
		}

		return true;
	}

	private void haveGooglePlayServices() {
		if (credential.getSelectedAccountName() == null) {
			chooseAccount();
		} else {
			load();
		}
	}

	private void chooseAccount() {
		startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case REQUEST_ACCOUNT_PICKER:
				if (resultCode == Activity.RESULT_OK && data != null && data.getExtras() != null) {
					String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
					
					if (accountName != null) {
						credential.setSelectedAccountName(accountName);
						User.getUser().setName(accountName);
						SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = settings.edit();
						editor.putString(PREF_ACCOUNT_NAME, accountName);
						editor.commit();
						
						load();
					}
				}
				break;
		}
	}
}
