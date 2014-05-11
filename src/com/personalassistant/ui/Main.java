package com.personalassistant.ui;

import java.io.IOException;
import java.util.Collections;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.personalassistant.R;
import com.personalassistant.services.CalendarAsyncTask;
import com.personalassistant.services.CalendarInfo;
import com.personalassistant.ui.navigation.NavigationDrawerFragment;
import com.personalassistant.ui.navigation.NavigationDrawerFragment.NavigationDrawerCallbacks;
import com.personalassistant.ui.navigation.NavigationMenuItem;

public class Main extends Activity implements NavigationDrawerCallbacks {
	private static final String PREF_ACCOUNT_NAME = "accountName";
	private static final int REQUEST_ACCOUNT_PICKER = 2;
	private static final int REQUEST_GOOGLE_PLAY_SERVICES = 0;
	private static final int REQUEST_AUTHORIZATION = 1;
	
	private NavigationDrawerFragment mNavigationDrawerFragment;

	private final HttpTransport transport = AndroidHttp.newCompatibleTransport();
	private final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
	private GoogleAccountCredential credential = null;
	private Calendar client = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

		credential = GoogleAccountCredential.usingOAuth2(this, Collections.singleton(CalendarScopes.CALENDAR));
		SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
		String accountName = settings.getString(PREF_ACCOUNT_NAME, null);
		credential.setSelectedAccountName(accountName);
		client = new com.google.api.services.calendar.Calendar.Builder(
				transport, jsonFactory, credential).setApplicationName(
				"Personal_Assistent").build();
		
		new CalendarAsyncTask(client) {
			
			@Override
			protected void onPreExec() throws IOException {
				
			}
			
			@Override
			protected void onPostExec() throws IOException {
				
			}
			
			@Override
			protected void doInBackground() throws IOException {
				try {
					CalendarList calendarList = null;
					calendarList = client.calendarList().list().setFields(CalendarInfo.FEED_FIELDS).execute();
					
					for (CalendarListEntry calendarToAdd : calendarList.getItems()) {
						System.out.println(calendarToAdd.getId());
					}
						
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.execute();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (checkGooglePlayServicesAvailable()) {
			haveGooglePlayServices();
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
			// AsyncLoadCalendars.run(this);
		}
	}

	private void chooseAccount() {
		startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
	      case REQUEST_GOOGLE_PLAY_SERVICES:
	        if (resultCode == Activity.RESULT_OK) {
	          haveGooglePlayServices();
	        } else {
	          checkGooglePlayServicesAvailable();
	        }
	        break;
	      case REQUEST_AUTHORIZATION:
	        if (resultCode == Activity.RESULT_OK) {
	          // AsyncLoadCalendars.run(this);
	        } else {
	          chooseAccount();
	        }
	        break;
	      case REQUEST_ACCOUNT_PICKER:
	        if (resultCode == Activity.RESULT_OK && data != null && data.getExtras() != null) {
	          String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
	          if (accountName != null) {
	            credential.setSelectedAccountName(accountName);
	            SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
	            SharedPreferences.Editor editor = settings.edit();
	            editor.putString(PREF_ACCOUNT_NAME, accountName);
	            editor.commit();
	          }
	        }
	        break;
		}
	}

	@Override
	public void onNavigationDrawerItemSelected(NavigationMenuItem item) {
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, item.getFragment()).commit();
	}
}
