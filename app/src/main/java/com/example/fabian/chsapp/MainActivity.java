package com.example.fabian.chsapp;

import android.app.Activity;
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

    public void add(View view) {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }

    public void send(View view) {
        Intent intent = new Intent(this, ChooseContactActivity.class);
        startActivity(intent);
    }
}
