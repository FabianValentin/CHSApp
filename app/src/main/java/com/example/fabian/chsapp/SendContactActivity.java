package com.example.fabian.chsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SendContactActivity extends AppCompatActivity {

    private ListView lv;
    private String name;
    private String number;
    ArrayList<String> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_contact);
        Intent intent = getIntent();
        contacts = intent.getStringArrayListExtra("contacts");
        lv = findViewById(R.id.contactList);
        lv.setAdapter(new ArrayAdapter<String>(SendContactActivity.this, android.R.layout.simple_list_item_1, contacts));
    }

    protected void generateQR(View view) {
        Intent intent = new Intent(this, GenerateQrActivity.class);
        intent.putExtra("contacts",contacts);
        startActivity(intent);
    }

    protected void sendEmail(View view) {
        Intent intent = new Intent(this, SendEmail.class);
        intent.putExtra("contacts",contacts);
        startActivity(intent);
    }
}
