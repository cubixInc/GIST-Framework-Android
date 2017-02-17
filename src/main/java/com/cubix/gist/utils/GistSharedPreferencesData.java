package com.cubix.gist.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;


public class GistSharedPreferencesData {

	private SharedPreferences settings;

	private static final String PREFS_NAME = "gist";
	private static final String RESOURCE_LAST_SYNCTIME = "resource_last_synctime";
	private static final String RESOURCE_SYNCTIME = "resource_synctime";

	public GistSharedPreferencesData(Context context) {

		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	}

	public void setResourceSyncTime(String resourceSyncTime) {

		SharedPreferences.Editor editor = settings.edit();
		editor.putString(RESOURCE_SYNCTIME + "_" + Locale.getDefault().getLanguage(), resourceSyncTime);

		// Commit the edits!
		editor.commit();
	}

	public String getResourceSyncTime() {

		return settings.getString(RESOURCE_SYNCTIME + "_" + Locale.getDefault().getLanguage(), "");
	}

	public void setResourceLastSyncTime(long lastSyncTime) {

		SharedPreferences.Editor editor = settings.edit();
		editor.putLong(RESOURCE_LAST_SYNCTIME + "_" + Locale.getDefault().getLanguage(), lastSyncTime);

		// Commit the edits!
		editor.commit();
	}

	public long getResourceLastSyncTime() {

		return settings.getLong(RESOURCE_LAST_SYNCTIME + "_" + Locale.getDefault().getLanguage(), 0);
	}
}
