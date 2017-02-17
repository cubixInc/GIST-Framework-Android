package com.cubix.gist.parser;


import com.cubix.gist.httpclients.GeneralResponseData;
import com.cubix.gist.models.SyncResourceModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class GistJsonParser {

    public static GeneralResponseData getGeneralResponse(String response) {

        GeneralResponseData generalData = null;

        try {
            generalData = new GeneralResponseData();

            JSONObject jsonData = new JSONObject(response);
            generalData.setError(jsonData.getInt("error"));
            generalData.setMessage(jsonData.getString("message"));

            if (jsonData.has("kick_user")) {

                generalData.setKickUser(jsonData.getInt("kick_user"));
            }

            if (jsonData.has("data")) {
                generalData.setData(jsonData.optString("data"));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return generalData;
    }

    public static SyncResourceModel getSyncResources(String data) {

        Gson gson = new Gson();
        SyncResourceModel syncResourceModel = gson.fromJson(data, SyncResourceModel.class);

        return syncResourceModel;
    }

}
