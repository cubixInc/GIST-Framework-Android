package com.cubix.gist.synresource;

import android.app.Activity;
import android.app.ProgressDialog;

import com.cubix.gist.GistApplication;
import com.cubix.gist.httpclients.GeneralResponseData;
import com.cubix.gist.httpclients.GistHttpRequestHandler;
import com.cubix.gist.httpclients.IResponseHandler;
import com.cubix.gist.models.ResourceModel;
import com.cubix.gist.models.SyncResourceModel;
import com.cubix.gist.parser.GistJsonParser;
import com.cubix.gist.utils.GistConstants;
import com.cubix.gist.utils.GistUtils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Created by imran.pyarali on 5/18/2016.
 */
public class SyncHelper implements IResponseHandler<GeneralResponseData> {

    private Activity activity;
    private ISyncComplete syncCallback;
    private GistApplication gistApplication;
    private ProgressDialog pd;
    private String baseUrl;
    private String requestMethod;
    private Map headers;

    public SyncHelper(Activity activity, String baseUrl, String requestMethod, Map headers) {

        this.activity = activity;
        this.baseUrl = baseUrl;
        this.requestMethod = requestMethod;
        this.headers = headers;
        syncCallback = (ISyncComplete)activity;
        gistApplication = (GistApplication) activity.getApplication();
        pd = new ProgressDialog(activity);
    }

    public void syncUpdatedResources() {

        if (isThreshold()) {

            sendSyncRequest();
        }
    }

    private boolean isThreshold() {

        boolean isThreshold = false;
        long lastSyncTime = gistApplication.getResourceLastSyncTime();
        long currentSyncTime = System.currentTimeMillis();

        if (lastSyncTime <= 0) {

            isThreshold = true;

        } else {

            long diffSyncTime = currentSyncTime - lastSyncTime;
            //ResourceManager resourceManager = gistApplication.getResourceManager();
            if (diffSyncTime >= GistUtils.convertHourToMilliseconds(SyncResourceManager.getConstantFloatValue("sync_resource_threshold"))) {

                isThreshold = true;
            }
        }

        return isThreshold;
    }

    public void onLocaleChange() {

        sendSyncRequest();
    }

    private Map getSyncRequestParams() {

        Map params = new HashMap<>();;

        if (!gistApplication.getResourceSyncTime().isEmpty()) {

            params.put("updated_at", gistApplication.getResourceSyncTime());
        }

        params.put("language", Locale.getDefault().getLanguage() /*gistApplication.getLocale()*/);

        return params;
    }

    /*private Map getRequestHeaders() {

        Map<String, String> mapHeaders = null;
        if (headers != null && !headers.isEmpty()) {

            Gson gson = new Gson();

            Type type = new TypeToken<Map<String, String>>(){}.getType();
            mapHeaders = gson.fromJson(headers, type);
        }

        return mapHeaders;
    }*/

    private void sendSyncRequest() {

        GistHttpRequestHandler request = new GistHttpRequestHandler(activity, GistConstants.SYNC_ENGINE_REQUEST_CODE, GistHttpRequestHandler.HTTP_REQUEST_POST, false);
        request.setBaseUrl(baseUrl);
        request.setHeaders(headers);
        request.setCallback(this);
        Map params = getSyncRequestParams();

        GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "params: " + params.toString());

        if (params != null && params.size() > 0) {


            request.setPostParams(params);
        }

        request.sendRequest(requestMethod);
    }

    private void updateSyncData(SyncResourceModel syncResourceModel) {

        gistApplication.setResourceSyncTime(syncResourceModel.getUpdatedAt());
        ResourceModel resourceModel = syncResourceModel.getSyncData();
        ResourceManager resourceManager = SyncResourceManager.getInstance().getResourceManager();

        //String Resource
        updateResources(resourceModel.getText(), resourceManager.getStringProperty());

        //Color Resource
        updateResources(resourceModel.getColor(), resourceManager.getColorProperty());

        //Font Resource
        updateResources(resourceModel.getFontStyle(), resourceManager.getFontProperty());

        //Constant Resource
        updateResources(resourceModel.getConstant(), resourceManager.getConstantProperty());
    }

    private void updateResources(Properties properties, GistProperty gistProperty) {

        if (!properties.isEmpty()) {

            Enumeration propEnum = properties.propertyNames();

            while(propEnum.hasMoreElements()) {

                String key = (String)propEnum.nextElement();
                gistProperty.setPropertyData(key, properties.getProperty(key));
            }

            gistProperty.writeProperty();
        }
    }

    @Override
    public void onSuccess(GeneralResponseData response, int request_code) {

        switch (request_code) {

            case GistConstants.SYNC_ENGINE_REQUEST_CODE:

                String data =  response.getData();
                SyncResourceModel syncResourceModel = GistJsonParser.getSyncResources(data);
                gistApplication.setResourceLastSyncTime(System.currentTimeMillis());
                updateSyncData(syncResourceModel);
                GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "updated_at: " + syncResourceModel.getUpdatedAt());

                syncCallback.onComplete();
                break;
        }
    }

    @Override
    public void onError(GeneralResponseData response, int request_code) {

        GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "Sync onError");
    }

    @Override
    public void onFailure(int request_code, int response_code) {

        GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "Sync onFailure");
    }

    @Override
    public void onInternetConnectionFailure(int request_code) {

        GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "Sync onInternetConnectionFailure");
    }

    @Override
    public void onRequestCancel(int request_code) {

        GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "Sync onRequestCancel");
    }
}
