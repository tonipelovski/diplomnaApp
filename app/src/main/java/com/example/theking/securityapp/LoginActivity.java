package com.example.theking.securityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

        RequestQueue queue = Volley.newRequestQueue(this);
        String url =" http://localhost/Diplomna_Software/server/login.php?username="+ emailValue+ "&password="+ passwordValue;

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

    private void saveSpecialCodeOnDevice(String specialCode){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("specialCode", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("code", specialCode);
        editor.apply();
    }
}
