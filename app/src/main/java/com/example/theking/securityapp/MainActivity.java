package com.example.theking.securityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private Button currentStateButton;
    private Button logInfoButton;

    private ArrayList<String> ids;
    private ArrayList<String> latitude;
    private ArrayList<String> longitude;
    private ArrayList<String> IMU;
    private ArrayList<String> shock;
    private ArrayList<String> date;
    private ArrayList<String> rfid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stopService(new Intent(this, NotifyService.class));
        Toast.makeText(getApplicationContext(), getUsername(), Toast.LENGTH_LONG).show();
        currentStateButton = findViewById(R.id.currentStateButton);
        logInfoButton = findViewById(R.id.infoLogButton);

        ids = new ArrayList<>();
        latitude = new ArrayList<>();
        longitude = new ArrayList<>();
        IMU = new ArrayList<>();
        shock = new ArrayList<>();
        date = new ArrayList<>();
        rfid = new ArrayList<>();

        if(getUsername().equals("No name defined")){
            Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(loginActivity);
            MainActivity.this.finish();
        }else{
            startService(new Intent(this, NotifyService.class));

            currentStateButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadCurrentState(0);

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

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadCurrentState(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        String username = getUsername();
        if (username == null || username.equals("")){
            //error
        }else {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://78.130.176.59/server/devices.php?apicall=1&username=" + username + "&page=" + offset*10;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Configure the RecyclerView
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            if(!response.isEmpty()) {
                                String[] splitedModules = response.split("/");

                                for(int i = 0; i < splitedModules.length; i++){
                                    Toast.makeText(getApplicationContext(), splitedModules[i], Toast.LENGTH_LONG).show();

                                    String[] splitedModuleData = splitedModules[i].split(";");

                                    //Toast.makeText(getApplicationContext(), splitedModuleData[1], Toast.LENGTH_LONG).show();
                                    ids.addAll(Arrays.asList(splitedModuleData[0]));
                                    date.addAll(Arrays.asList(splitedModuleData[1]));
                                    latitude.addAll(Arrays.asList(splitedModuleData[2]));
                                    longitude.addAll(Arrays.asList(splitedModuleData[3]));
                                    IMU.addAll(Arrays.asList(splitedModuleData[4]));
                                    shock.addAll(Arrays.asList(splitedModuleData[5]));
                                    rfid.addAll(Arrays.asList(splitedModuleData[6]));

                                }
                                ArrayList<String> information = new ArrayList<>();
                                information.add(ids.get(0));
                                information.add(date.get(0));
                                information.add(latitude.get(0));
                                information.add(longitude.get(0));
                                information.add(IMU.get(0));
                                information.add(shock.get(0));
                                information.add(rfid.get(0));

                                Intent gpsActivity = new Intent(MainActivity.this, GPSActivity.class);
                                gpsActivity.putStringArrayListExtra("info", information);
                                MainActivity.this.startActivity(gpsActivity);                            }

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

}
