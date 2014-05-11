package com.personalassistant.ui;

import java.io.IOException;
import java.util.Collections;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Calendars;
import android.support.v4.widget.DrawerLayout;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.calendar.CalendarScopes;
import com.personalassistant.R;
import com.personalassistant.services.CalendarAsyncTask;
import com.personalassistant.ui.navigation.NavigationDrawerFragment;
import com.personalassistant.ui.navigation.NavigationDrawerFragment.NavigationDrawerCallbacks;
import com.personalassistant.ui.navigation.NavigationMenuItem;

public class Main extends Activity implements NavigationDrawerCallbacks {
	public static final String[] EVENT_PROJECTION = new String[] {
	    Calendars._ID,                           // 0
	    Calendars.ACCOUNT_NAME,                  // 1
	    Calendars.CALENDAR_DISPLAY_NAME,         // 2
	};
	  
	private static final int PROJECTION_ID_INDEX = 0;
	private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
	private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
	
	private static final String PREF_ACCOUNT_NAME = "accountName";
	
	private static final int REQUEST_ACCOUNT_PICKER = 2;
	
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private GoogleAccountCredential credential = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

		setupSettings();
	}
			
	private  void startAppContent() {
		new CalendarAsyncTask() {
			@Override
			protected void onPreExec() throws IOException {
				
			}
			
			@Override
			protected void onPostExec() throws IOException {
				
			}
			
			@Override
			protected void doInBackground() throws IOException {
				ContentResolver contentResolver = getContentResolver();
				
				Uri uri = Calendars.CONTENT_URI;   
				String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND (" + Calendars.ACCOUNT_TYPE + " = ?))";
				
				String[] selectionArgs = new String[] { credential.getSelectedAccountName(), "com.google", }; 
				Cursor cur = contentResolver.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
				
				while (cur.moveToNext()) {
				    long calID = 0;
				    String displayName = null;
				    String accountName = null;
				      
				    calID = cur.getLong(PROJECTION_ID_INDEX);
				    displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
				    accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
				              
				    System.out.println(calID  + " - " + displayName + " - " + accountName + " - "/* + ownerName*/); 
				}
			}
		}.execute();
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

	@Override
	public void onNavigationDrawerItemSelected(NavigationMenuItem item) {
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, item.getFragment()).commit();
	}
}
