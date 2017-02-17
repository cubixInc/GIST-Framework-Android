package com.cubix.gist.httpclients;

public interface IResponseHandler<T> {

    public void onSuccess(T response, int request_code);
    public void onError(T response, int request_code);
    public void onFailure(int request_code, int response_code);
    public void onInternetConnectionFailure(int request_code);
    public void onRequestCancel(int request_code);

}
