package com.example.theking.securityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private String passwordValue;
    private String emailValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText email = (EditText) findViewById(R.id.emailLogField);
        emailValue = email.getText().toString();

        EditText password = (EditText) findViewById(R.id.passwordLogField);
        passwordValue = email.getText().toString();
    }

    private void saveSpecialCodeOnDevice(String specialCode){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("specialCode", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("code", specialCode);
        editor.apply();
    }
}
