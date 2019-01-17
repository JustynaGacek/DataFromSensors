package com.example.justy.DataFromSensors;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class SplashActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();

    public static ArrayList<JSONArray> jsonArrayDay;
    public static ArrayList<JSONArray> jsonArrayWeek;
    public static ArrayList<JSONArray> jsonArrayMonth;
    public static ArrayList<JSONArray> jsonArrayYear;

    public static GetAvaliableStationsRequest avaliableStationsRequest;
    public static GetAvailableDataRequest avaliableDataRequest;

    public GetMeasurementsDataRequest getMeasurementsDataRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        jsonArrayDay = new ArrayList<>();
        jsonArrayWeek = new ArrayList<>();
        jsonArrayMonth = new ArrayList<>();
        jsonArrayYear = new ArrayList<>();

        avaliableStationsRequest = new GetAvaliableStationsRequest();
        avaliableDataRequest = new GetAvailableDataRequest();

        getMeasurementsDataRequest = new GetMeasurementsDataRequest();

        new Thread(new Runnable(){
            @Override
            public void run() {

                try {
                    avaliableStationsRequest.getDataAndSaveAsJsonArray(client);
                    avaliableStationsRequest.parseJsonToVariables();
                    ArrayList<String> stationNames = avaliableStationsRequest.getStationsNames();

                    avaliableDataRequest.getDataAndSaveAsJsonArray(client);


                    for (int i = 0; i < stationNames.size(); i++) {

                        getMeasurementsDataRequest.getMeasurementsDataAndSaveAsJSONArray(client,"day", stationNames.get(i));
                        jsonArrayDay.add(getMeasurementsDataRequest.getResponseArray());

                        getMeasurementsDataRequest.getMeasurementsDataAndSaveAsJSONArray(client,"week", stationNames.get(i));
                        jsonArrayWeek.add(getMeasurementsDataRequest.getResponseArray());

                        getMeasurementsDataRequest.getMeasurementsDataAndSaveAsJSONArray(client,"month", stationNames.get(i));
                        jsonArrayMonth.add(getMeasurementsDataRequest.getResponseArray());

                        getMeasurementsDataRequest.getMeasurementsDataAndSaveAsJSONArray(client,"year", stationNames.get(i));
                        jsonArrayYear.add(getMeasurementsDataRequest.getResponseArray());
                    }
                    System.out.println(jsonArrayYear.size());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Intent mainViewIntent = new Intent(SplashActivity.this, MainViewActivity.class);
                    startActivity(mainViewIntent);
                    finish();
                }
            }
        }).start();

    }
}