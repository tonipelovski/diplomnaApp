package com.example.theking.securityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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


        RequestQueue queue = Volley.newRequestQueue(this);
        String url =" http://localhost/Diplomna_Software/server/register.php?username="+ emailValue+ "&password="+ passwordValue;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
