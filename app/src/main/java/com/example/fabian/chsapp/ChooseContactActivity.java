package com.example.fabian.chsapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ChooseContactActivity extends ListActivity {

    ListView list;
    Button button;
    String selectedName = "";
    String selectedNumber = "";
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contact);
        list = findViewById(R.id.list);
        button = findViewById(R.id.choose);
    }

    public void onListItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
        arrayList = new ArrayList<>(Arrays.asList(from));
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked, R.id.list_item,arrayList);
        list.setAdapter(arrayAdapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
        /*list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, from));
        CheckedTextView contact = (CheckedTextView)view;
        selectedName = from[i];
        Toast.makeText(this, from[i] + " checked : " +
                contact.isChecked(), Toast.LENGTH_SHORT).show();*/
    }


    public void setupEndActivityButton(View view) {
        Intent intent = new Intent();
        intent.putExtra("contactName", selectedName);
        intent.putExtra("contactNumber", selectedNumber);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}