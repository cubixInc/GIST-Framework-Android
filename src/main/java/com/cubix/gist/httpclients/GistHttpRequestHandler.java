package com.cubix.gist.httpclients;


import android.app.Activity;

import com.cubix.gist.dialogs.CustomProgressDialog;
import com.cubix.gist.parser.GistJsonParser;
import com.cubix.gist.utils.GistConstants;
import com.cubix.gist.utils.GistUtils;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GistHttpRequestHandler {

    public static final int HTTP_REQUEST_GET = 0;
    public static final int HTTP_REQUEST_POST = 1;
    public static final int HTTP_REQUEST_MULTIPART = 2;
    public static final int READ_TIME_OUT = 10;
    public static final int WRITE_TIME_OUT = 2000;
    public static final int CONNECTION_TIME_OUT = 10;
    private static final int TOTAL_RETRIES = 3;
    public static final int ADMIN_KICK_USER = 1;
    public static final int HTTP_OK = 200;
    public static final int HTTP_NO_RESPONSE = -1;

    private Activity activity;
    private CustomProgressDialog pd;
    private int requestCode;
    private boolean bProgressDialog;
    private boolean retry = false;
    private String baseUrl;
    private int requestType;
    private Map postParams;
    private Map fileParams;
    private Map headers;
    private int retryCount = 0;
    private IResponseHandler<GeneralResponseData> callback;
    private Call<ResponseBody> call;
    private boolean rawData = false;

    public GistHttpRequestHandler() {
        // TODO Auto-generated constructor stub
    }

    public GistHttpRequestHandler(Activity activity, int requestCode) {
        // TODO Auto-generated constructor stub

        this.activity = activity;
        this.requestCode = requestCode;
        this.bProgressDialog = true;
        pd = new CustomProgressDialog(activity);
    }

    public GistHttpRequestHandler(Activity activity, int requestCode, boolean bProgressDialog) {
        // TODO Auto-generated constructor stub

        this.activity = activity;
        this.requestCode = requestCode;
        this.bProgressDialog = bProgressDialog;
        pd = new CustomProgressDialog(activity);
    }

    public GistHttpRequestHandler(Activity activity, int requestCode, int requestType) {
        // TODO Auto-generated constructor stub

        this.activity = activity;
        this.requestCode = requestCode;
        this.bProgressDialog = true;
        this.requestType = requestType;
        pd = new CustomProgressDialog(activity);
    }

    public GistHttpRequestHandler(Activity activity, int requestCode, int requestType, boolean bProgressDialog) {
        // TODO Auto-generated constructor stub

        this.activity = activity;
        this.requestCode = requestCode;
        this.bProgressDialog = bProgressDialog;
        this.requestType = requestType;
        pd = new CustomProgressDialog(activity);
    }

    public boolean isRawData() {
        return rawData;
    }

    public void setRawData(boolean rawData) {
        this.rawData = rawData;
    }

    public boolean isRetry() {
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }

    public IResponseHandler<GeneralResponseData> getCallback() {
        return callback;
    }

    public void setCallback(IResponseHandler<GeneralResponseData> callback) {
        this.callback = callback;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Map getHeaders() {
        return headers;
    }

    public void setHeaders(Map headers) {
        this.headers = headers;
    }

    public Map getPostParams() {
        return postParams;
    }

    public void setPostParams(Map postParams) {
        this.postParams = postParams;
    }

    public Map getFileParams() {
        return fileParams;
    }

    public void setFileParams(Map fileParams) {
        this.fileParams = fileParams;
    }

    public void sendRequest(String requestUrl) {

        if(GistUtils.isInternetConnected(activity)) {

            if (activity != null && !activity.isFinishing() && pd != null && !pd.isShowing()  && bProgressDialog) {
                pd.show();
            }

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(new BasicAuthInterceptor(headers))
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RestApi service = retrofit.create(RestApi.class);
            String requestPath = /*GistConstants.RELATIVE_PATH +*/ requestUrl;

            switch (requestType) {

                case HTTP_REQUEST_GET:

                    call = (postParams != null)
                            ? service.getRequest(requestPath,postParams)
                            : service.getRequest(requestPath);
                    break;

                case HTTP_REQUEST_POST:

                    call = (postParams != null)
                            ? service.postRequest(requestPath, postParams)
                            : service.postRequest(requestPath);
                    break;

                case HTTP_REQUEST_MULTIPART:

                    call = (postParams != null)
                            ? service.multiRequest(requestPath,  fileParams, postParams)
                            : service.multiRequest(requestPath,  fileParams);
                    break;

            }

            call.enqueue(responseBodyCallback);

        }  else {

            handleDismissDialog();
            callback.onInternetConnectionFailure(requestCode);
        }

    }

    Callback<ResponseBody> responseBodyCallback =   new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            handleDismissDialog();

            if(response.body() !=  null && response.raw().code() == HTTP_OK){

                String jsonData = null;
                try {
                    jsonData = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(jsonData != null) {

                    if(rawData) {

                        GeneralResponseData generalResponseData = new GeneralResponseData();
                        generalResponseData.setData(jsonData);

                        callback.onSuccess(generalResponseData, requestCode);

                    }  else {

                        GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "response: " + jsonData);
                        GeneralResponseData generalResponseData = GistJsonParser.getGeneralResponse(jsonData);

                        if (generalResponseData.getKickUser() == ADMIN_KICK_USER) {

                            // logout code

                        }  else {

                            if (generalResponseData.getError() == 0) {

                                // onSuccess
                                callback.onSuccess(generalResponseData, requestCode);

                            } else {

                                //onError
                                callback.onError(generalResponseData, requestCode);
                            }
                        }
                    }

                } else {

                    //onFailure
                    callback.onFailure(requestCode, response.raw().code());
                }

            } else {

                //onFailure
                callback.onFailure(requestCode, response.raw().code());
            }
        }

        @Override
        public void onFailure(final Call<ResponseBody> call2, Throwable t) {


            if(t instanceof SocketTimeoutException && retry) {

                if (retryCount++ < TOTAL_RETRIES && call != null) {

                    call.clone().enqueue(responseBodyCallback);

                } else {

                    handleDismissDialog();
                    callback.onFailure(requestCode, HTTP_NO_RESPONSE);

                }

            } else if(t instanceof SocketException) {

                callback.onRequestCancel(requestCode);

            } else {

                handleDismissDialog();
                callback.onFailure(requestCode, HTTP_NO_RESPONSE);

            }

        }

    };

    public void cancelRequest() {

        if(call != null) {

            call.cancel();
        }

    }

    public void handleDismissDialog() {

        try {

            if (activity != null && !activity.isFinishing() && pd != null && pd.isShowing() && bProgressDialog) {
                pd.dismiss();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public static void setMultiPartFile(Map map, String keyName, String path) {

        File file = new File(path);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        String filename = path.substring(path.lastIndexOf("/")+1);
        map.put(""+keyName+"\"; filename=\""+filename+"\"", requestBody);
    }
}
