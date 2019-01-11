package com.example.justy.DataFromSensors;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetMeasurementsDataRequest extends PrepareResponseBase {

    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String url = "https://station-controll-1.localtunnel.me/request_data/";

    private JSONArray responseArray;

    void getMeasurementsDataAndSaveAsJSONArray(OkHttpClient client, String timeRange, String station) {

        String jsonToSend = createJsonToSend(timeRange, station);

        RequestBody body = RequestBody.create(JSON, jsonToSend);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


        try {
            Response response = client.newCall(request).execute();
            responseArray = new JSONArray(transformResponseString(response.body().string()));
//            response.body().close();
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