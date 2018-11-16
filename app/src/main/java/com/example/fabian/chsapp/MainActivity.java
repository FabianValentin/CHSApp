package com.example.fabian.chsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startScanQR(View view) {
        Intent intent = new Intent(this, ScanQrActivity.class);
        startActivity(intent);
    }

    public void startGenerateQR(View view) {
        Intent intent = new Intent(this, GenerateQrActivity.class);
        startActivity(intent);
    }
}
