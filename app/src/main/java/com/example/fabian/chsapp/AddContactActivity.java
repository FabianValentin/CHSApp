package com.example.fabian.chsapp;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AddContactActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_FOR_CONTACTS_QR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

    public void scanQr(View view) {
        Intent intent = new Intent(this, ScanQrActivity.class);
        startActivityForResult(intent,REQUEST_CODE_FOR_CONTACTS_QR);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FOR_CONTACTS_QR) {
            if(resultCode == RESULT_OK) {
                ArrayList<String> contactsToBeSaved = data.getStringArrayListExtra("contacts");
                saveContacts(contactsToBeSaved);
                Intent intent = new Intent(this, EndActivity.class);
                intent.putExtra("text","Contact(s) Saved");
                intent.putExtra("finalContacts", contactsToBeSaved);
                startActivity(intent);
            }
        }
    }

    public void scanEmail(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData data = clipboardManager.getPrimaryClip();
        try{
            ClipData.Item contact = data.getItemAt(0);
            String conn = contact.toString().substring(contact.toString().indexOf("Name"));
            String connt = conn.replace("}","");
            Pattern p = Pattern.compile("\\n[\\n]+");
            String[] con = p.split(connt.toString());
            int i;
            ArrayList<String> contactsToBeSaved = new ArrayList<>();
            for (i = 0; i < con.length; i++) {
                contactsToBeSaved.add(con[i]);
            }
            saveContacts(contactsToBeSaved);
            Intent intent = new Intent(this, EndActivity.class);
            intent.putExtra("text", "Contact(s) Saved");
            intent.putExtra("finalContacts", contactsToBeSaved);
            startActivity(intent);
        }catch (Exception e) {
            Toast.makeText(this,"No input.Please copy the contacts in clipboard",Toast.LENGTH_SHORT).show();
        }
    }

    public void saveContacts(ArrayList<String> contacts){
        int i;
        for(i=0;i<contacts.size();i++) {
            String cname = getName(contacts.get(i).toString());;
            String cnumber= getNumber(contacts.get(i).toString());

            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());

            //------------------------------------------------------ Names
            if (cname != null) {
                ops.add(ContentProviderOperation.newInsert(
                        ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(
                                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                cname).build());
            }

            //------------------------------------------------------ Mobile Number
            if (cnumber != null) {
                ops.add(ContentProviderOperation.
                        newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, cnumber)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());
            }

            // Asking the Contact provider to create a new contact
            try {
                getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                Toast.makeText(this, "Contact: " + cname + " saved", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
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
