package com.example.fabian.chsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class SendContactActivity extends AppCompatActivity {

    TextView contactName;
    TextView contactNumber;
    String name;
    String number;
    ArrayList<String> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_contact);
        Intent intent = getIntent();
        /*name = intent.getStringExtra("contactName");
        number = intent.getStringExtra("contactNumber");*/
        contacts = intent.getStringArrayListExtra("contacts");
        contactName = findViewById(R.id.contactName);
        //contactName.setText(name);
        contactNumber = findViewById(R.id.contactNumber);
        //contactNumber.setText(number);
    }

    protected void generateQR(View view) {
        Intent intent = new Intent(this, GenerateQrActivity.class);
        /*intent.putExtra("contactName", name);
        intent.putExtra("contactNumber", number);*/
        intent.putExtra("contacts",contacts);
        startActivity(intent);
    }

    protected void sendEmail(View view) {
        Intent intent = new Intent(this, SendEmail.class);
        /*intent.putExtra("contactName", name);
        intent.putExtra("contactNumber", number);*/
        intent.putExtra("contacts",contacts);
        startActivity(intent);
    }
}
