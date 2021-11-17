package com.zulfikar.spinnerlist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnContinue;
    Spinner spinBroadcast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinBroadcast = findViewById(R.id.spinBroadcast);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(v -> {
            int selectedOperation = spinBroadcast.getSelectedItemPosition();
            Intent secondActivity;
            if (selectedOperation == 1) secondActivity = new Intent(MainActivity.this, ThirdActivity.class);
            else secondActivity = new Intent(MainActivity.this, SecondActivity.class);
            secondActivity.putExtra("broadcastOperation", selectedOperation);
            startActivity(secondActivity);
        });
    }
}