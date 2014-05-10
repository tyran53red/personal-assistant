package com.personalassistant.ui;

import java.io.IOException;
import java.util.Collections;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

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
import com.personalassistant.services.CalendarInfo;
import com.personalassistant.ui.navigation.NavigationDrawerFragment;
import com.personalassistant.ui.navigation.NavigationDrawerFragment.NavigationDrawerCallbacks;
import com.personalassistant.ui.navigation.NavigationMenuItem;

public class Main extends Activity implements NavigationDrawerCallbacks {
	private static final String PREF_ACCOUNT_NAME = "accountName";
	
	private NavigationDrawerFragment mNavigationDrawerFragment;

	private final HttpTransport transport = AndroidHttp.newCompatibleTransport();
	private final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
	private GoogleAccountCredential credential = null;
	private Calendar client = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		credential = GoogleAccountCredential.usingOAuth2(this, Collections.singleton(CalendarScopes.CALENDAR));
		SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
		credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
		client = new com.google.api.services.calendar.Calendar.Builder(
				transport, jsonFactory, credential).setApplicationName(
				"Personal_Assistent").build();

		try {
			CalendarList size = client.calendarList().list().setFields(CalendarInfo.FEED_FIELDS).execute();
			for (CalendarListEntry calendarToAdd : size.getItems()) {
				System.out.println(calendarToAdd.getId());
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setContentView(R.layout.main);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(NavigationMenuItem item) {
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, item.getFragment()).commit();
	}
}
