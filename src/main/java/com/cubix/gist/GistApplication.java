package com.cubix.gist;

import android.app.Application;
import android.content.res.Configuration;

import com.cubix.gist.synresource.SyncResourceManager;
import com.cubix.gist.utils.GistConstants;
import com.cubix.gist.utils.GistSharedPreferencesData;
import com.cubix.gist.utils.GistUtils;

import java.util.Locale;

/**
 * Created by imran.pyarali on 5/12/2016.
 */
public class GistApplication extends Application {

    private GistSharedPreferencesData sharedPreferencesData;
    private String resourceSyncTime;
    private long resourceLastSyncTime;

    @Override
    public void onCreate() {
        super.onCreate();

        GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "onCreate locale: " + Locale.getDefault().getLanguage());
        sharedPreferencesData = new GistSharedPreferencesData(getApplicationContext());
        resourceSyncTime = sharedPreferencesData.getResourceSyncTime();
        resourceLastSyncTime = sharedPreferencesData.getResourceLastSyncTime();

        //SyncResourceManager.initInstance(getApplicationContext(), GistConstants.HTTP_BASE_URL, GistConstants.REQUEST_SYNC_ENGINE, "{'api_email':'salman.khimani@cubixlabs.com','api_password':'apipass123'}");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //locale = newConfig.locale.getLanguage();
        GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "onConfigurationChanged locale: " + Locale.getDefault().getLanguage());
        resourceSyncTime = sharedPreferencesData.getResourceSyncTime();
        resourceLastSyncTime = sharedPreferencesData.getResourceLastSyncTime();

        SyncResourceManager.onLocaleChange(getApplicationContext());
    }

    public GistSharedPreferencesData getSharedPreferencesData() {

        return sharedPreferencesData;
    }

    public String getResourceSyncTime() {

        return resourceSyncTime;
    }

    public void setResourceSyncTime(String resourceSyncTime) {

        this.resourceSyncTime = resourceSyncTime;
        sharedPreferencesData.setResourceSyncTime( resourceSyncTime);
    }

    public long getResourceLastSyncTime() {

        return resourceLastSyncTime;
    }

    public void setResourceLastSyncTime(long resourceLastSyncTime) {

        this.resourceLastSyncTime = resourceLastSyncTime;
        sharedPreferencesData.setResourceLastSyncTime(resourceLastSyncTime);
    }
}
