package com.example.justy.DataFromSensors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private final int splashScreenTime = 500;//5000; //in milliseconds
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
    }
//
//    public void connectionToDatabase() {
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                // All your networking logic
//                // should be here
//                // Create URL
//                try {
//                    URL githubEndpoint = new URL("https://api.github.com/");
//
//                    // Create connection
//                    HttpsURLConnection myConnection = (HttpsURLConnection) githubEndpoint.openConnection();
//                }catch (IOException e){
//
//                }
//
//
//            }
//        });
//    }
}
