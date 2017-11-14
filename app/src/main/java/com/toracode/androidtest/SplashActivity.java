package com.toracode.androidtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    // Currently using this, will only show the Splash screen until the App is ready to go (almost instantly for now).
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    // Use this if you want to set a fixed amount of time to wait (e.g. 3s).
    /**
     * @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread welcomeThread = new Thread() {

        @Override
        public void run() {
            try {
                super.run();
                sleep(3000)  //Delay of 3 seconds
            } catch (Exception e) {

                } finally {

                Intent intent = new Intent(SplashActivity.this,
                HomeActivity.class);
                startActivity(intent);
                finish();
                }
            }
        };
        welcomeThread.start();
    }
     */
}
