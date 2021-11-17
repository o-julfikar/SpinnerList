package com.zulfikar.spinnerlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

public class CustomBroadcastReceiver extends BroadcastReceiver {

    TextView textView;

    public CustomBroadcastReceiver(TextView textView) {
        this.textView = textView;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onReceive(Context context, Intent intent) {
        String broadcastText = "";
        boolean updateBroadcast = false;

        if ("com.zulfikar.spinnerList.CUSTOM_BROADCAST".equals(intent.getAction())) {
            broadcastText = intent.getStringExtra("com.zulfikar.spinnerList.CUSTOM_TEXT");
            updateBroadcast = true;
        } else if ("com.zulfikar.spinnerList.BATTERY_BROADCAST".equals(intent.getAction())) {
            broadcastText = "USER: " + intent.getStringExtra("com.zulfikar.spinnerList.BATTERY_PERCENTAGE") + "%";
            updateBroadcast = true;
        }
        if (updateBroadcast) textView.setText(broadcastText);
    }
}
