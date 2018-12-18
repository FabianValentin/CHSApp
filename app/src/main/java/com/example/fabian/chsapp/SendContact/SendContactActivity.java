package com.example.fabian.chsapp.SendContact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.fabian.chsapp.R;
import java.util.ArrayList;

public class SendContactActivity extends AppCompatActivity {

    private ListView contactsList;
    ArrayList<String> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_contact);
        Intent intent = getIntent();
        //se preiau contactele selectate
        contacts = intent.getStringArrayListExtra("contacts");
        contactsList = findViewById(R.id.contactList);
        //se afiseaza contactele selectate
        contactsList.setAdapter(new ArrayAdapter<String>(SendContactActivity.this, android.R.layout.simple_list_item_1, contacts));
    }

    // 2 posibilitati de trimitere:
    //prin QR
    protected void generateQR(View view) {
        Intent intent = new Intent(this, GenerateQrActivity.class);
        intent.putExtra("contacts",contacts);
        startActivity(intent);
    }

    //prin email, aplicatii de socializare, folosind clip-board
    protected void sendEmail(View view) {
        Intent intent = new Intent(this, SendEmail.class);
        intent.putExtra("contacts",contacts);
        startActivity(intent);
    }
}
