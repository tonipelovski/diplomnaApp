package com.example.theking.securityapp;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;

public class NotifyService extends IntentService {
    public NotifyService() {
        super("notify");
    }
    private NotificationCompat.Builder builder;
    private NotificationManagerCompat notificationManager;
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        createNotificationChannel();
        builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle("notification")
                .setContentText("paqka ti vdig kolata")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        while (true){
            loadNextDataFromApi(0);
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
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

                                for(int i = 0; i < 1; i++){
                                    String[] splitedModuleData = splitedModules[i].split(";");
                                    String id = splitedModuleData[0];
                                    if(Integer.parseInt(splitedModuleData[4]) == 1)notificationManager.notify(1, builder.build());
                                    if(Integer.parseInt(splitedModuleData[5]) == 1)notificationManager.notify(1, builder.build());
                                    if(Integer.parseInt(splitedModuleData[6]) == 1)notificationManager.notify(1, builder.build());

                                }

                                //check the data for errors
                                // if errors then notify
                                //notificationManager.notify(1, builder.build());

                            }

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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "notification";
            String description = "notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private String getUsername(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("userName", 0);
        return settings.getString("name", "No name defined");
    }

}
