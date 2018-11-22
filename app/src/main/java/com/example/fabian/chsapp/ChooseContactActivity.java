package com.example.fabian.chsapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseContactActivity extends ListActivity {

    private Button button;
    private ListView lv;
    private List<String> contacte = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contact);
        lv = findViewById(android.R.id.list);
        lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        button = findViewById(R.id.choose);
        loadContacts();
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, contacte));

    }

    private void loadContacts(){

        String build;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

        if(cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                build = "";
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    Cursor cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?",
                            new String[]{id}, null);
                    while (cursor2.moveToNext()) {
                        String phoneNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        build = name + " " + phoneNumber;
                        contacte.add(build);
                    }
                    cursor2.close();
                }

            }
        }
        cursor.close();

    }


    public void setupEndActivityButton(View view) {
        int p = lv.getCheckedItemPosition();
        if(p!=ListView.INVALID_POSITION) {
            String s = ((TextView)lv.getChildAt(p)).getText().toString();
            int lastSpace = s.lastIndexOf(' ' );
            String name = s.substring(0, lastSpace);
            String number = s.substring(lastSpace);
            Intent intent = getIntent();
            intent.putExtra("contactName", name);
            intent.putExtra("contactNumber", number);
            setResult(Activity.RESULT_OK, intent);
            finish();
            //Toast.makeText(MainActivity.this, "Selected item is " + s, Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Nothing Selected..", Toast.LENGTH_LONG).show();
        }

    }
}