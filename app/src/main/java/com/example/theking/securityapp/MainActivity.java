package com.example.theking.securityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), getUsername(), Toast.LENGTH_LONG).show();
        if(getUsername().equals("No name defined")){
            Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(loginActivity);
        }else{
            Intent informationActivity = new Intent(MainActivity.this, InformationActivity.class);
            MainActivity.this.startActivity(informationActivity);
        }
        //Intent gpsActivity = new Intent(MainActivity.this, GPSActivity.class);
        //MainActivity.this.startActivity(gpsActivity);
    }

    private String getUsername(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("userName", 0);
        return settings.getString("name", "No name defined");
    }
}
