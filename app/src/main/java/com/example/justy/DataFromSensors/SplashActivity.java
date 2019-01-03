package com.example.justy.DataFromSensors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private final int splashScreenTime = 15000;//5000; //in milliseconds
    Thread splashTread;

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

//

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
                    GlobalVariables.postRequestPerWeek.post("week", "Krakow, ul. Kujawska 1");
                    GlobalVariables.postRequestPerDay.post("day", "Krakow, ul. Kujawska 1");
                    //sleep(2000);

                    //sleep(2000);
                    GlobalVariables.postRequestPerMonth.post("month", "Krakow, ul. Kujawska 1");
                    //sleep(2000);
                    GlobalVariables.postRequestPerYear.post("year", "Krakow, ul. Kujawska 1");
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