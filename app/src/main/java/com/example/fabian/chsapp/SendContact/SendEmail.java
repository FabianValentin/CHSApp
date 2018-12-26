package com.example.fabian.chsapp.SendContact;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fabian.chsapp.Contact;

import java.util.ArrayList;

public class SendEmail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        //se preiau contactele ce vor fi trimise trimis
        ArrayList<Contact> contacts = (ArrayList<Contact>)intent.getSerializableExtra("contacts");

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        StringBuilder body = new StringBuilder();

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your contact");
        String name; String number;
        //se formeaza continutul: contactele care vor fi trimise
        for( int i = 0 ; i < contacts.size() ; i++) {
            body.append(contacts.get(i));
            body.append("\n\n");
        }
        //se seteaza textul
        emailIntent.putExtra(Intent.EXTRA_TEXT,body.toString());
        try {
             //se ofera posibilitatea utilizatorului de a alege prin ce aplicatie vor fi trimise contactele
             startActivity(Intent.createChooser(emailIntent, "Send mail..."));
             finish();
        } catch (android.content.ActivityNotFoundException ex) {
             Toast.makeText(SendEmail.this,
                  "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
