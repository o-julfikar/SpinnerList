package com.zulfikar.spinnerlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.net.wifi.rtt.WifiRttManager;
import android.os.Bundle;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    TextView txtBroadcastText;
    private CustomBroadcastReceiver customBroadcast;
    private BroadcastReceiver batteryBroadcast, wifiBroadcast;
    private boolean customBCRegistered, batteryBCRegistered, wifiBCRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        boolean supportWifiRTT = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WIFI_RTT);
        txtBroadcastText = findViewById(R.id.txtBroadcastText);
        customBroadcast = new CustomBroadcastReceiver(txtBroadcastText);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.zulfikar.spinnerList.CUSTOM_BROADCAST");
        filter.addAction("com.zulfikar.spinnerList.BATTERY_BROADCAST");
        registerReceiver(customBroadcast, filter);
        customBCRegistered = true;

        IntentFilter batteryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                    txtBroadcastText.setText(txtBroadcastText.
                            getText().toString().split("\nSYSTEM")[0].concat("\nSYSTEM: ").
                            concat(intent.getIntExtra("level", -1) + "%"));
                }
            }
        };

        IntentFilter wifiFilter = new IntentFilter();
        if (supportWifiRTT) wifiFilter.addAction(WifiRttManager.ACTION_WIFI_RTT_STATE_CHANGED);
        else wifiFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);

        wifiBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                    int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
                    String wifiRTTStat = "\nSupports WiFi RTT: " + supportWifiRTT;
                    if (state == WifiManager.WIFI_STATE_ENABLED) {
                        txtBroadcastText.setText(getString(R.string.wifi_status_on).concat(wifiRTTStat));
                    } else if (state == WifiManager.WIFI_STATE_DISABLED) {
                        txtBroadcastText.setText(getString(R.string.wifi_status_off).concat(wifiRTTStat));
                    }
                }
            }
        };

        int broadcastOperation = getIntent().getIntExtra("broadcastOperation", -1);
        String broadcastData = getIntent().getStringExtra("broadcastData");

        Intent intent = new Intent();
        if (broadcastOperation == 0) {
            intent.setAction("com.zulfikar.spinnerList.CUSTOM_BROADCAST");
            intent.putExtra("com.zulfikar.spinnerList.CUSTOM_TEXT", broadcastData);
        } else if (broadcastOperation == 2) {
            intent.setAction("com.zulfikar.spinnerList.BATTERY_BROADCAST");
            intent.putExtra("com.zulfikar.spinnerList.BATTERY_PERCENTAGE", broadcastData);
        }
        sendBroadcast(intent);
        if (broadcastOperation == 2) {
            registerReceiver(batteryBroadcast, batteryFilter);
            batteryBCRegistered = true;
        }
        if (broadcastOperation == 1) {
            registerReceiver(wifiBroadcast, wifiFilter);
            wifiBCRegistered = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (customBCRegistered) unregisterReceiver(customBroadcast);
        if (batteryBCRegistered) unregisterReceiver(batteryBroadcast);
        if (wifiBCRegistered) unregisterReceiver(wifiBroadcast);
    }
}