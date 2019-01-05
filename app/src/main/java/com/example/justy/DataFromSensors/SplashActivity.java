package com.example.justy.DataFromSensors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private final int splashScreenTime = 30000;//5000; //in milliseconds
    Thread splashTread;

//    public static PostRequest postRequestPerDay;
//
//    public static PostRequest postRequestPerWeek;
//
//    public static PostRequest postRequestPerMonth;
//
//    public static PostRequest postRequestPerYear;

    public static ArrayList<JSONArray> jsonArrayDay = new ArrayList<>();
    public static ArrayList<JSONArray> jsonArrayWeek = new ArrayList<>();
    public static ArrayList<JSONArray> jsonArrayMonth = new ArrayList<>();
    public static ArrayList<JSONArray> jsonArrayYear = new ArrayList<>();

    public static GetAvaliableStationsRequest avaliableStationsRequest = new GetAvaliableStationsRequest();
    public static GetAvailableDataRequest avaliableDataRequest = new GetAvailableDataRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        splashTread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    synchronized (this) {
//
//                        wait(splashScreenTime);
//                    }
//                } catch (InterruptedException e) {
//                } finally {
//                    Intent mainViewIntent = new Intent(SplashActivity.this, MainViewActivity.class);
//                    startActivity(mainViewIntent);
//                    finish();
//                }
//            }
//        };
//        splashTread.start();



        new Thread(new Runnable(){
            @Override
            public void run() {

                try {
                    // Do network action in this function
                    avaliableStationsRequest.get();
                    avaliableStationsRequest.parseJsonToVariables();
                    ArrayList<String> stationNames = avaliableStationsRequest.getStationsNames();

                    avaliableDataRequest.get();

//                PostRequest postRequestPerDay = new PostRequest();
//                postRequestPerDay.post("day", "Gmina Skawina, Zelczyna");

//                try {
//                    GlobalVariables.parseJSON.clearArrays();
//                    GlobalVariables.parseJSON.getDataFromJSON(postRequestPerDay.getResponseArray(), "Temperatura");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                    PostRequest postRequestPerDay = new PostRequest();
                    PostRequest postRequestPerWeek = new PostRequest();
                    PostRequest postRequestPerMonth = new PostRequest();
                    PostRequest postRequestPerYear = new PostRequest();
                    for (int i = 0; i < stationNames.size(); i++) {

                        postRequestPerDay.post("day", stationNames.get(i));
                        jsonArrayDay.add(postRequestPerDay.getResponseArray());
                        System.out.println("dzien");
                        //System.out.println(jsonArrayDay);

                        postRequestPerWeek.post("week", stationNames.get(i));
                        jsonArrayWeek.add(postRequestPerWeek.getResponseArray());
                        System.out.println("tydzien");
                        //System.out.println(jsonArrayWeek);

                        postRequestPerMonth.post("month", stationNames.get(i));
                        jsonArrayMonth.add(postRequestPerMonth.getResponseArray());
                        System.out.println("miesiac");
                        //System.out.println(jsonArrayMonth);

                        postRequestPerYear.post("year", stationNames.get(i));
                        jsonArrayYear.add(postRequestPerYear.getResponseArray());
                        System.out.println("rok");
                        //System.out.println(jsonArrayYear);
                    }
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