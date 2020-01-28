package com.example.theking.securityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private String emailValue;
    private String passwordValue;
    private String passwordConfirmationValue;
    private String specialCodeValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText email = findViewById(R.id.emailField);
        EditText password = findViewById(R.id.passwordField);
        EditText passwordConfirmation = findViewById(R.id.passwordConfirmationField);
        EditText specialCode = findViewById(R.id.specialCode);

        emailValue = email.getText().toString();
        passwordValue = password.getText().toString();
        passwordConfirmationValue = passwordConfirmation.getText().toString();
        specialCodeValue = specialCode.getText().toString();

    }
}
