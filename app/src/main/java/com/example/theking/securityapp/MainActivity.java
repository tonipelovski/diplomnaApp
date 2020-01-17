package com.example.theking.securityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent gpsActivity = new Intent(MainActivity.this, GPSActivity.class);
        MainActivity.this.startActivity(gpsActivity);
    }
}
