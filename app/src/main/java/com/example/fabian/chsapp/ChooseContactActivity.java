package com.example.fabian.chsapp;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.util.SparseBooleanArray;
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
import java.util.List;

public class ChooseContactActivity extends ListActivity {

    private Button button;
    private ListView lv;
    private List<String> contacte = new ArrayList<>();
    private String s = "";
    private CheckedTextView ctv;
    private ArrayList<String> contacteChecked = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contact);
        lv = findViewById(android.R.id.list);
        lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        button = findViewById(R.id.choose);
        loadContacts();
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, contacte));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(contacteChecked.contains(contacte.get(i)))
                    contacteChecked.remove(contacte.get(i));
                else
                    contacteChecked.add(contacte.get(i));
            }
        });
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
                        if(!contacte.contains(build))
                            contacte.add(build);
                    }
                    cursor2.close();
                }
            }
        }
        cursor.close();
        java.util.Collections.sort(contacte);
    }

    public void setupEndActivityButton(View view) {
        int noContacts = lv.getCheckedItemCount();
        if(noContacts == 0){
            Toast.makeText(view.getContext(),"You have not select any contact",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, SendContactActivity.class);
            intent.putExtra("contacts", contacteChecked);
            startActivity(intent);
        }
    }
}