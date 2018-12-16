package com.example.fabian.chsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EndActivity extends AppCompatActivity {

    private TextView finalText;
    private ListView finalContactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_save);

        finalText = findViewById(R.id.finalText);
        finalContactsList = findViewById(R.id.finalContacts);

        Intent intent = getIntent();
        String text = intent.getStringExtra("text");
        ArrayList<String> finalContacts = intent.getStringArrayListExtra("finalContacts");
        finalText.setText(text);
        finalContactsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, finalContacts));
    }
}
