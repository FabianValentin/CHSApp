package com.example.fabian.chsapp.SendContact;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fabian.chsapp.Contact;
import com.example.fabian.chsapp.MainActivity;
import com.example.fabian.chsapp.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ChooseContactActivity extends AppCompatActivity {

    private List<Contact> contacts = new ArrayList<>();
    private ArrayList<Contact> checked_contacts = new ArrayList<>();

    private ListView listcontacts;
    private List<String> contacte = new ArrayList<>();
    private ArrayList<String> contacteMarcate = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    // Search EditText
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contact);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, contacte);

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                ChooseContactActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        listcontacts = findViewById(android.R.id.list);
        //CHOICE_MODE_MULTIPLE -> pentru a permite selectarea multipla a contactelor
        listcontacts.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        loadContacts();
        listcontacts.setAdapter(adapter);

        listcontacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(checked_contacts.contains(contacts.get(i))) {
                    checked_contacts.remove(contacts.get(i));
                }
                else {
                    checked_contacts.add(contacts.get(i));
                }
            }
        });
    }

    //se incarca contactele din lista telefonului
    private void loadContacts(){

        String contact="";
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

        if(cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                contact = "";
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

                        // Making sure that 0724 313 158 is the same as 0724313158.
                        phoneNumber = phoneNumber.replaceAll(" ", "");
                        Contact contactw = new Contact(name, phoneNumber);

                        //verificare pentru a nu avea duplicate (ex contact salvat in SIM, whatsApp, messenger => 3 duplicate)
                        if(!contacts.contains(contactw)) {
                            contacts.add(contactw);
                            contacte.add(contactw.s());
                        }
                    }
                    cursor2.close();
                }
            }
        }
        cursor.close();
        //sortam alfabetic lista de contacte
        java.util.Collections.sort(contacte);
        java.util.Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void setupEndActivityButton(View view) {
        int noContacts = listcontacts.getCheckedItemCount();
        if(noContacts == 0){
            //daca nu s-a selectat niciun contact se va afisa un mesaj
            Toast.makeText(view.getContext(),"You have not selected any contact",Toast.LENGTH_SHORT).show();
        }
        else {
            //avem contacte selectate, deci putem trece la urmatorul pas, selectarea modului prin care se vor trimite
            Intent intent = new Intent(this, SendContactActivity.class);
            java.util.Collections.sort(checked_contacts, new Comparator<Contact>() {
                @Override
                public int compare(Contact o1, Contact o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            intent.putExtra("contacts", checked_contacts);
            startActivity(intent);
        }
    }
}