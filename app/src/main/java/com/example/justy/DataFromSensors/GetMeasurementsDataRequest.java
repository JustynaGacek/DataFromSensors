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
    private final String endpoint = "request_data/";

    private JSONArray responseArray;

    public void getMeasurementsDataAndSaveAsJSONArray(OkHttpClient client, String timeRange, String station) {

        String jsonToSend = createJsonToSend(timeRange, station);

        RequestBody body = RequestBody.create(JSON, jsonToSend);

        Request request = new Request.Builder()
                .url(ServerAddress.url + endpoint)
                .post(body)
                .build();


        try {
            Response response = client.newCall(request).execute();
            responseArray = new JSONArray(transformResponseString(response.body().string()));
        } catch (IOException e) {

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String createJsonToSend(String timeRange, String station){
        return "{\"Time\": \"" + timeRange + "\", \"Station\":\"" + station + "\"}";
    }

    public JSONArray getResponseArray() {
        return responseArray;
    }

}