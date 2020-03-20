package com.example.theking.securityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class ManualControl extends AppCompatActivity {
    private ArrayAdapter adapter;
    private ArrayList<String> ids = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_control);
        loadNextDataFromApi(0);
        for(String id : ids){
            if (getModuleAlarmStatus(id).equals("No id defined")) {
                updateAlarmModule(id, "ON");
            }
        }
        adapter = new ArrayAdapter<String>(this,R.layout.listview,ids);


        ListView listView = (ListView) findViewById(R.id.modules_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getModuleAlarmStatus(ids.get(position)).equals("ON")) {
                    updateAlarmModule(ids.get(position), "OFF");
                } else{
                    updateAlarmModule(ids.get(position), "ON");
                }
            }
        });

    }

    private String getModuleAlarmStatus(String id){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("modules", 0);
        return settings.getString(id, "No id defined");
    }

    private void updateAlarmModule(String id, String state){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("modules", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(id, state);
        editor.apply();
        Toast.makeText(getApplicationContext(), "State: " + state, Toast.LENGTH_LONG).show();
    }

    private String getUsername(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("userName", 0);
        return settings.getString("name", "No name defined");
    }


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
                            //Toast.makeText(getApplicationContext(), (int) System.currentTimeMillis(), Toast.LENGTH_LONG).show();

                            if(!response.isEmpty()) {
                                String[] splitedModules = response.split("/");

                                for(int i = 0; i < splitedModules.length; i+=10){
                                    String[] splitedModuleData = splitedModules[i].split(";");
                                    ids.add(splitedModuleData[0]);
                                    adapter.notifyDataSetChanged();
                                }


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

}
