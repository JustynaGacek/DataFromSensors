package com.example.justy.DataFromSensors;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostRequest {

    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    final String url = "https://station-controll-1.localtunnel.me/request_data/";

    private OkHttpClient client = new OkHttpClient();

    private static JSONArray responseArray;

    void post(String timeRange, String station) {

        String jsonToSend = createJsonToSend(timeRange, station);

        RequestBody body = RequestBody.create(JSON, jsonToSend);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            String response = client.newCall(request).execute().body().string();
            String responseAfterRemoveBackslash = response.replace("\\\"", "\"");
            responseAfterRemoveBackslash = responseAfterRemoveBackslash.substring(1, responseAfterRemoveBackslash.length() - 1);
            responseArray = new JSONArray(responseAfterRemoveBackslash);
        } catch (IOException e) {

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    String createJsonToSend(String timeRange, String station){
        return "{\"Time\": \"" + timeRange + "\", \"Station\":\"" + station + "\"}";
    }

    JSONArray getResponseArray() {
        return responseArray;
    }

}