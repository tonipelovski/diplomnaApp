package com.example.theking.securityapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       intent = new Intent(context, NotifyService.class);
       context.startService(intent);
    }
}
