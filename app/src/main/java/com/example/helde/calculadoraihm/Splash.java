package com.example.helde.calculadoraihm;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by helde on 01/05/2016.
 */
public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent startMainScreen = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(startMainScreen);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
