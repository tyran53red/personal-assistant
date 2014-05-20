package com.personalassistant.ui;

import java.util.Collections;

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
	
	private  void startAppContent() {
		App.setCredential(credential);
		
		Intent intent = new Intent(this, Main.class);
		startActivity(intent);
	}
	
	private void setupSettings() {
		if (credential == null) {
			credential = GoogleAccountCredential.usingOAuth2(this, Collections.singleton(CalendarScopes.CALENDAR));
		}

		SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
		String accountName = settings.getString(PREF_ACCOUNT_NAME, null);
		
		if (accountName != null) {
			credential.setSelectedAccountName(accountName);
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
			startAppContent();
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
						SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = settings.edit();
						editor.putString(PREF_ACCOUNT_NAME, accountName);
						editor.commit();
						
						startAppContent();
					}
				}
				break;
		}
	}
}
