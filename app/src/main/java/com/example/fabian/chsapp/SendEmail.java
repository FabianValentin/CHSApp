package com.example.fabian.chsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class SendEmail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        //se preiau contactele ce vor fi trimise trimis
        ArrayList<String> contacts = intent.getStringArrayListExtra("contacts");

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        StringBuilder body = new StringBuilder();

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your contact");
        String name; String number;
        //se formeaza continutul: contactele care vor fi trimise
        for( int i = 0 ; i < contacts.size() ; i++) {
            int lastSpace = contacts.get(i).lastIndexOf(' ' );
            name =  "Name: " + contacts.get(i).substring(0, lastSpace) + "\n";
            number = "Number: " + contacts.get(i).substring(lastSpace) + "\n";
            body.append(name);
            body.append(number);
            body.append("\n");
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
    private String getName (String s) {
        String search = "Name: ";
        return s.substring(s.indexOf(search) + search.length() , s.indexOf("\n"));
    }

    private String getNumber (String s) {
        String search = "Number: ";
        return s.substring(s.indexOf(search) + search.length());
    }
}
