package com.personalassistant.services;


public abstract class CalendarAsyncTask implements Runnable {
	
	@Override
	public void run() {
		boolean onPreExec = false;
		
		try {
			onPreExec();
			onPreExec = true;
		} catch (Exception e) {
			doException(e);
		}
		
		if (onPreExec) {
			boolean doInBackground = false;
			
			try {
				doInBackground();
				doInBackground = true;
			} catch (Exception e) {
				doException(e);
			}
			
			if (doInBackground) {
				try {
					onPostExec();
				} catch (Exception e) {
					doException(e);
				}
			}
		}
	}
	
	private void doException(Exception e) {
		e.printStackTrace();
	}

	protected abstract void onPreExec() throws Exception;
	protected abstract void doInBackground() throws Exception;
	protected abstract void onPostExec() throws Exception;
}
