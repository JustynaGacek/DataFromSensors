package com.example.justy.DataFromSensors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;

public class SplashActivity extends AppCompatActivity {

    private final int splashScreenTime = 20000;//5000; //in milliseconds
    Thread splashTread;

//    public static PostRequest postRequestPerDay;
//
//    public static PostRequest postRequestPerWeek;
//
//    public static PostRequest postRequestPerMonth;
//
//    public static PostRequest postRequestPerYear;

    public static JSONArray jsonArrayDay;
    public static JSONArray jsonArrayWeek;
    public static JSONArray jsonArrayMonth;
    public static JSONArray jsonArrayYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {

                        wait(splashScreenTime);
                    }
                } catch (InterruptedException e) {
                } finally {
                    Intent mainViewIntent = new Intent(SplashActivity.this, MainViewActivity.class);
                    startActivity(mainViewIntent);
                    finish();
                }
            }
        };
        splashTread.start();



        new Thread(new Runnable(){
            @Override
            public void run() {
                // Do network action in this function
                GlobalVariables.avaliableStationsRequest.get();
                GlobalVariables.avaliableDataRequest.get();

//                PostRequest postRequestPerDay = new PostRequest();
//                postRequestPerDay.post("day", "Gmina Skawina, Zelczyna");

//                try {
//                    GlobalVariables.parseJSON.clearArrays();
//                    GlobalVariables.parseJSON.getDataFromJSON(postRequestPerDay.getResponseArray(), "Temperatura");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


                try {
                    PostRequest postRequestPerDay = new PostRequest();
                    postRequestPerDay.post("day", "Krakow, ul. Kujawska 1");
                    jsonArrayDay = postRequestPerDay.getResponseArray();
                    System.out.println("dzien");
                    System.out.println(jsonArrayDay);

                    PostRequest postRequestPerWeek = new PostRequest();
                    postRequestPerWeek.post("week", "Krakow, ul. Kujawska 1");
                    jsonArrayWeek = postRequestPerWeek.getResponseArray();
                    System.out.println("tydzien");
                    System.out.println(jsonArrayWeek);

                    PostRequest postRequestPerMonth = new PostRequest();
                    postRequestPerMonth.post("month", "Krakow, ul. Kujawska 1");
                    jsonArrayMonth = postRequestPerMonth.getResponseArray();
                    System.out.println("miesiac");
                    System.out.println(jsonArrayMonth);

                    PostRequest postRequestPerYear = new PostRequest();
                    postRequestPerYear.post("year", "Krakow, ul. Kujawska 1");
                    jsonArrayYear = postRequestPerYear.getResponseArray();
                    System.out.println("rok");
                    System.out.println(jsonArrayYear);

                } catch (Exception e) {
                    e.printStackTrace();
                }








//                PostRequest postRequestPerDay = new PostRequest();
//                postRequestPerDay.post("day", "Gmina Skawina, Zelczyna");
////                System.out.println(postRequestPerDay.getResponseArray());
//
//                try {
//                    ParseJSON parseJSON = new ParseJSON();
//                    parseJSON.parseJson(postRequestPerDay.getResponseArray());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
            }
        }).start();

    }
}