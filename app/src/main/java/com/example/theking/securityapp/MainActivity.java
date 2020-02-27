package com.example.theking.securityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button currentStateButton;
    private Button logInfoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), getUsername(), Toast.LENGTH_LONG).show();
        currentStateButton = findViewById(R.id.currentStateButton);
        logInfoButton = findViewById(R.id.infoLogButton);
        if(getUsername().equals("No name defined")){
            Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(loginActivity);
            MainActivity.this.finish();
        }else{
            startService(new Intent(this, NotifyService.class));
            currentStateButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gpsActivity = new Intent(MainActivity.this, GPSActivity.class);
                    gpsActivity.putExtra("latitude", 0);
                    gpsActivity.putExtra("longitude", 0);
                    gpsActivity.putExtra("IMU", 0);
                    gpsActivity.putExtra("shock", 0);
                    MainActivity.this.startActivity(gpsActivity);
                }
            });
            logInfoButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent informationActivity = new Intent(MainActivity.this, InformationActivity.class);
                    MainActivity.this.startActivity(informationActivity);
                }
            });


        }
        //Intent gpsActivity = new Intent(MainActivity.this, GPSActivity.class);
        //MainActivity.this.startActivity(gpsActivity);
    }

    private String getUsername(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("userName", 0);
        return settings.getString("name", "No name defined");
    }
}
