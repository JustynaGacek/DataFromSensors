package com.example.justy.DataFromSensors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GetAvailableDataRequest {

    final String url = "https://station-controll-2.localtunnel.me/view_data_available/";

    private OkHttpClient client = new OkHttpClient();

    private JSONObject avaliableDataJSON;

    //avaliable columns names
    private String stationColumnName;
    private String idColumnName;
    private String timeMeasurementColumnName;
    private ArrayList<String> avaliableColumnsNames;
    private ArrayList<String> avaliableColumnsUnitsNames;

    GetAvailableDataRequest(){
        avaliableColumnsNames = new ArrayList<>();
        avaliableColumnsUnitsNames = new ArrayList<>();
    }

    public void get() {

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            String responseString = client.newCall(request).execute().body().string();
            String responseAfterRemoveBackslash = responseString.replace("\\\"", "\"");
            responseAfterRemoveBackslash = responseAfterRemoveBackslash.substring(1, responseAfterRemoveBackslash.length() - 1);
            avaliableDataJSON = new JSONObject(responseAfterRemoveBackslash);
            System.out.println();
            System.out.println(avaliableDataJSON.get("columns").getClass());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseJsonToVariables(){
        if(avaliableDataJSON!=null) {
            try {
                JSONArray avaliableDataJSONArray = (JSONArray) avaliableDataJSON.get("columns");
                for (int i = 0; i < avaliableDataJSONArray.length(); i++) {
                    if (i == 0) {
                        stationColumnName = avaliableDataJSONArray.getString(i);
                    } else if (i == 1) {
                        idColumnName = avaliableDataJSONArray.getString(i);
                    } else if (i == 2) {
                        timeMeasurementColumnName = avaliableDataJSONArray.getString(i);
                    } else {
                        if (!avaliableDataJSONArray.getString(i).contains("_jednostka")) {
                            avaliableColumnsNames.add(avaliableDataJSONArray.getString(i));
                        } else {
                            avaliableColumnsUnitsNames.add(avaliableDataJSONArray.getString(i));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void printAll() {
        System.out.print("Stacjaa:");
        System.out.println(stationColumnName);

        System.out.print("Idd:");
        System.out.println(idColumnName);

        System.out.print("Timee:");
        System.out.println(timeMeasurementColumnName);

        System.out.print("Columnn:");
        System.out.println(avaliableColumnsNames);

        System.out.print("Unitss:");
        System.out.println(avaliableColumnsUnitsNames);
    }

    public String getStationColumnName(){
        return stationColumnName;
    }

    public String getIdColumnName(){
        return idColumnName;
    }

    public String getTimeMeasurementColumnName(){
        return timeMeasurementColumnName;
    }

    public ArrayList<String> getAvaliableColumnsNames(){
        return avaliableColumnsNames;
    }

    public  ArrayList<String> getAvaliableColumnsUnitsNames(){
        return avaliableColumnsUnitsNames;
    }
}
