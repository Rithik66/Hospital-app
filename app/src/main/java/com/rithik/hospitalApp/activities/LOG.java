package com.rithik.hospitalApp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.rithik.hospitalApp.R;

public class LOG extends AppCompatActivity {

    TextView text;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        text = findViewById(R.id.text_view);
        intent = getIntent();
        text.setText(intent.getStringExtra("log"));
    }
}