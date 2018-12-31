package com.example.justy.DataFromSensors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private final int splashScreenTime = 10000;//5000; //in milliseconds
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


        new Thread(new Runnable(){
            @Override
            public void run() {
                // Do network action in this function
                GlobalVariables.avaliableDataRequest.get();
                GlobalVariables.postRequest.post("day", "Gmina Skawina, Zelczyna");
//                PostRequest postRequest = new PostRequest();
//                postRequest.post("day", "Gmina Skawina, Zelczyna");
////                System.out.println(postRequest.getResponseArray());
//
//                try {
//                    ParseJSON parseJSON = new ParseJSON();
//                    parseJSON.parseJson(postRequest.getResponseArray());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
            }
        }).start();

    }
}