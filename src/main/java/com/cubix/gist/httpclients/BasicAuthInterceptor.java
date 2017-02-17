package com.cubix.gist.httpclients;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mehran.khan on 3/25/2016.
 */
public class BasicAuthInterceptor implements Interceptor {

    private Map headerMap;

    public BasicAuthInterceptor() {

    }

    public BasicAuthInterceptor(Map headerMap) {

        this.headerMap = headerMap;
    }

    private void setHeaders(Request.Builder builder) {

        if (headerMap != null) {

            Iterator it = headerMap.entrySet().iterator();

            while (it.hasNext()) {

                Map.Entry pair = (Map.Entry)it.next();
                builder.header(pair.getKey().toString(), pair.getValue().toString());
            }
        }
    }

    /*@Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("api_email", GistConstants.API_EMAIL)
                .header("api_password", GistConstants.API_PASSWORD)
                .build();
        return chain.proceed(authenticatedRequest);
    }*/

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        setHeaders(builder);
        Request authenticatedRequest = builder.build();

        return chain.proceed(authenticatedRequest);
    }
}