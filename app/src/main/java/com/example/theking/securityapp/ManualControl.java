package com.example.theking.securityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManualControl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_control);
        //tuka zaqvkata
        final ArrayList<String> ids = new ArrayList<>();
        ids.add(String.valueOf(20022));
        for(String id : ids){
            if (getModuleAlarmStatus(id).equals("No id defined")) {
                updateAlarmModule(id, "ON");
            }
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.listview,ids);


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
}
