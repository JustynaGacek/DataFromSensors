package com.example.justy.DataFromSensors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;

public class SplashActivity extends AppCompatActivity {

    private final int splashScreenTime = 5000;//5000; //in milliseconds
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

                PostRequest postRequest = new PostRequest();
                postRequest.post("day", "Gmina Skawina, Zelczyna");
//                System.out.println(postRequest.getResponseArray());

                try {
                    ParseJSON parseJSON = new ParseJSON();
                    parseJSON.parseJson(postRequest.getResponseArray());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//
            }
        }).start();

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                // Create URL
//                try {
//                    URL githubEndpoint = new URL("https://nervous-liger-43.localtunnel.me/home/");
//
//                    HttpURLConnection myConnection = (HttpURLConnection) githubEndpoint.openConnection();
//                }
//                catch (Exception e) {
//                    Log.d("blad", "dddd");
//                }
//            }
//        });
    }
}