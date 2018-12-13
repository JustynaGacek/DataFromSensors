package com.example.justy.DataFromSensors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private final int splashScreenTime = 5000; //in milliseconds
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
}
