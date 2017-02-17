package com.cubix.gist.httpclients;

public class GeneralResponseData {

    private int error;
    private String message; // occur if response = error
    private String data;
    private int setKickUser;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getKickUser() {
        return setKickUser;
    }

    public void setKickUser(int setKickUser) {
        this.setKickUser = setKickUser;
    }
}
