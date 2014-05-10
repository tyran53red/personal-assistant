package com.personalassistant.services;

import java.io.IOException;

import android.os.AsyncTask;

import com.google.api.services.calendar.Calendar;

public abstract class CalendarAsyncTask extends AsyncTask<Void, Void, Boolean> {
	protected CalendarModel model = null;
	protected Calendar client = null;

	public CalendarAsyncTask(Calendar client) {
		model = CalendarModel.getInstance();
		this.client = client;
	}

	@Override
	protected void onPreExecute() {
		try {
			onPreExec();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		super.onPreExecute();
	}

	@Override
	protected final Boolean doInBackground(Void... ignored) {
		try {
			doInBackground();
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	protected final void onPostExecute(Boolean success) {
		try {
			onPostExec();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		super.onPostExecute(success);
	}

	protected abstract void onPreExec() throws IOException;
	protected abstract void doInBackground() throws IOException;
	protected abstract void onPostExec() throws IOException;
}
