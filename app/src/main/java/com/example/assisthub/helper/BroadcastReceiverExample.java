package com.example.assisthub.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadcastReceiverExample extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String phone = intent.getStringExtra("phone");
        String message = intent.getStringExtra("message");

        // Show the received message
        Toast.makeText(context, "Received code: " + message, Toast.LENGTH_LONG).show();

    }
}
