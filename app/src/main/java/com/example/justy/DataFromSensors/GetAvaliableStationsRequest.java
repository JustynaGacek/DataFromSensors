package com.example.justy.DataFromSensors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GetAvaliableStationsRequest {

    final String url = "https://station-controll-4.localtunnel.me/view_stations_available/";

    private OkHttpClient client = new OkHttpClient();

    private JSONArray avaliableStationsJSON;

    //avaliable columns names
    private ArrayList<Integer> stationsIds;
    private ArrayList<String> stationsNames;

    GetAvaliableStationsRequest(){
        stationsIds = new ArrayList<>();
        stationsNames = new ArrayList<>();
    }

    public void get() throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            String responseString = client.newCall(request).execute().body().string();
            String responseAfterRemoveBackslash = responseString.replace("\\\"", "\"");
            responseAfterRemoveBackslash = responseAfterRemoveBackslash.substring(1, responseAfterRemoveBackslash.length() - 1);
            avaliableStationsJSON = new JSONArray(responseAfterRemoveBackslash);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseJsonToVariables(){
        if(avaliableStationsJSON!=null) {
            for (int i = 0; i < avaliableStationsJSON.length(); i++) {
                try {
                    JSONObject object = avaliableStationsJSON.getJSONObject(i);
                    stationsIds.add(object.getInt("pk"));
                    JSONObject objectFields = (JSONObject) object.get("fields");
                    stationsNames.add(objectFields.getString("Name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void printAll() {
        System.out.print("StationsIdss:");
        System.out.println(stationsIds);

        System.out.print("StationsNamess:");
        System.out.println(stationsNames);
    }

    public ArrayList<Integer> getStationsIds(){
        return stationsIds;
    }

    public ArrayList<String> getStationsNames(){
        return stationsNames;
    }
}
