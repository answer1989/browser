package com.example.browser;

import model.BrowserDatabaseHelper;
import android.app.Application;

public class BrowserApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		BrowserDatabaseHelper.init(getApplicationContext());
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		BrowserDatabaseHelper.destroy();
	}
}
