package com.cubix.gist.synresource;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.cubix.gist.utils.GistUtils;

import java.util.Map;

/**
 * Created by imran.pyarali on 6/23/2016.
 */
public class SyncResourceManager {

    private static SyncResourceManager syncResourceManager;
    private ResourceManager resourceManager;
    private SyncHelper syncHelper;

    private String baseUrl;
    private String requestMethod;
    private Map headers;

    private SyncResourceManager(Context context, String baseUrl, String requestMethod, Map headers) {

        resourceManager = new ResourceManager(context);
        this.baseUrl = baseUrl;
        this.requestMethod = requestMethod;
        this.headers = headers;
    }

    public static void initInstance(Context context, String baseUrl, String requestMethod, Map headers) {

        if (syncResourceManager == null) {

            syncResourceManager = new SyncResourceManager(context, baseUrl, requestMethod, headers);
        }
    }

    public void initSyncHelper(Activity activity) {

        syncHelper = new SyncHelper(activity, baseUrl, requestMethod, headers);
    }

    public static void onLocaleChange(Context context) {

        if (syncResourceManager != null) {

            syncResourceManager.getResourceManager().updateStringResource(context);
        }
    }

    public static SyncResourceManager getInstance() {

        return syncResourceManager;
    }

    public ResourceManager getResourceManager() {

        return resourceManager;
    }

    public SyncHelper getSyncHelper() {

        return syncHelper;
    }

    public static int getColor(String key) {

        return Color.parseColor(syncResourceManager.getResourceManager().getColorProperty().getPropertyData(key));
    }

    public static String getString(String key) {

        return syncResourceManager.getResourceManager().getStringProperty().getPropertyData(key);
    }

    public static float getFontSize(String key) {

        String value = syncResourceManager.getResourceManager().getFontProperty().getPropertyData(key);
        value = value.replace("sp", "");

        float size = (!value.isEmpty()) ? GistUtils.toFloat(value) : 14;

        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, size, metrics);
    }

    public static float getFontSize(String key, int unit) {

        String value = syncResourceManager.getResourceManager().getFontProperty().getPropertyData(key);
        value = value.replace("sp", "");

        float size = (!value.isEmpty()) ? GistUtils.toFloat(value) : 14;

        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(unit, size, metrics);
    }

    public static int getConstantIntValue(String key) {

        return GistUtils.toInt(syncResourceManager.getResourceManager().getConstantProperty().getPropertyData(key));
    }

    public static long getConstantLongValue(String key) {

        return GistUtils.toLong(syncResourceManager.getResourceManager().getConstantProperty().getPropertyData(key));
    }

    public static float getConstantFloatValue(String key) {

        return GistUtils.toFloat(syncResourceManager.getResourceManager().getConstantProperty().getPropertyData(key));
    }

    public static double getConstantDoubleValue(String key) {

        return GistUtils.toDouble(syncResourceManager.getResourceManager().getConstantProperty().getPropertyData(key));
    }

    public static String getConstantValue(String key) {

        return syncResourceManager.getResourceManager().getConstantProperty().getPropertyData(key);
    }

    public static String getFont(String key) {

        return syncResourceManager.getResourceManager().getConstantProperty().getPropertyData(key);
    }
}
