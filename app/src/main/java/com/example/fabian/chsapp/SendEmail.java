package com.example.fabian.chsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class SendEmail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        ArrayList<String> contacts = intent.getStringArrayListExtra("contacts");
        /*String name = intent.getStringExtra("contactName");
        String number = intent.getStringExtra("contactNumber");
*/

        Log.i("Send email", "");


        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        StringBuilder b = new StringBuilder();

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your contact");
        for( int i = 0 ; i < contacts.size() ; i++) {
            //emailIntent.putExtra(Intent.EXTRA_TEXT, "Name: " +  getName(contacts.get(i)) + "\n" + "Number: " +  getNumber(contacts.get(i))+ "\n");
            b.append(contacts.get(i));
            b.append("\n");
        }
        emailIntent.putExtra(Intent.EXTRA_TEXT,b.toString());
        try {
             startActivity(Intent.createChooser(emailIntent, "Send mail..."));
             finish();
             Log.i("Finished sending email", "");
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
