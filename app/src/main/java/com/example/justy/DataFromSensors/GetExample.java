package com.example.justy.DataFromSensors;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetExample {

    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public JSONArray doPostRequest (String url, String json) throws IOException, JSONException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String afterRemove = response.toString().replace("\\\"", "\"");
        afterRemove = afterRemove.substring(1, afterRemove.length()-1);

        JSONArray ja = new JSONArray(afterRemove);
        return ja;

    }
}
