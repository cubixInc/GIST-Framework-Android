package com.cubix.gist.httpclients;


import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by androidwarriors on 12/3/2015.
 */
public interface RestApi {

    @GET("{path}")
    Call<ResponseBody> getRequest(@Path(value = "path", encoded = true) String path);

    @GET("{path}")
    Call<ResponseBody> getRequest(@Path(value = "path", encoded = true) String path, @QueryMap Map<String, String> params);

    @POST("{path}")
    Call<ResponseBody> postRequest(@Path(value = "path", encoded = true) String path);

    @FormUrlEncoded
    @POST("{path}")
    Call<ResponseBody> postRequest(@Path(value = "path", encoded = true) String path, @FieldMap Map<String, String> params);

    @Multipart
    @POST("{path}")
    Call<ResponseBody> multiRequest(@Path(value = "path", encoded = true) String path, @PartMap Map<String, RequestBody> Files);

    @Multipart
    @POST("{path}")
    Call<ResponseBody> multiRequest(@Path(value = "path", encoded = true) String path, @PartMap Map<String, RequestBody> Files, @PartMap Map<String, String> params);

}
