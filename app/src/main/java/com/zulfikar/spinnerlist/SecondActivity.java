package com.zulfikar.spinnerlist;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    Button btnContinue;
    EditText txtUserInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnContinue = findViewById(R.id.btnContinue);
        txtUserInput = findViewById(R.id.txtUserInput);

        int broadcastOperation = getIntent().getIntExtra("broadcastOperation", -1);
        Intent activity3 = new Intent(SecondActivity.this, ThirdActivity.class);

        if (broadcastOperation == 0) {
            txtUserInput.setHint(R.string.hint_custom_receiver);
            txtUserInput.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (broadcastOperation == 2) {
            txtUserInput.setHint(R.string.hint_battery_percentage);
            txtUserInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        btnContinue.setOnClickListener(v -> {
            activity3.putExtra("broadcastOperation", broadcastOperation);
            activity3.putExtra("broadcastData", txtUserInput.getText().toString());
            SecondActivity.this.startActivity(activity3);
        });
    }
}