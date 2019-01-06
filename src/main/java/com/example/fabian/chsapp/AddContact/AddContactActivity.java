package com.example.fabian.chsapp.AddContact;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fabian.chsapp.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AddContactActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_FOR_CONTACTS_QR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

    //2 moduri de adaugarea a contactelor
    //prin scanarea unui QR
    public void scanQr(View view) {
        Intent intent = new Intent(this, ScanQrActivity.class);
        //asteptam confirmarea faptului ca s-a reusit sscanarea contactelor
        startActivityForResult(intent,REQUEST_CODE_FOR_CONTACTS_QR);
    }

    //receptionarea contactelor dupa scanarea qr-ului
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FOR_CONTACTS_QR) {
            if(resultCode == RESULT_OK) {
                //primirea contactelor scanate
                ArrayList<String> contactsToBeSaved = data.getStringArrayListExtra("contacts");

                for(String con:contactsToBeSaved) {
                    if (!con.matches(".*Name:.*\nNumber:.*")) {
                        Toast.makeText(this, "QR does not contain any contact", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        //salvarea contactelor
                        if(saveContacts(contactsToBeSaved))
                        {//afisarea confirmarii faptului ca au fost salvate contactele
                            Intent intent = new Intent(this, EndActivity.class);
                            intent.putExtra("text", "Contact(s) Saved");
                            intent.putExtra("finalContacts", contactsToBeSaved);
                            startActivity(intent);
                        }
                    }
                }
            }
        }
    }

    //prin copierea textului din clip-board
    public void scanEmail(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData data = clipboardManager.getPrimaryClip();
        try{
            //copierea contactelor din clip-board
            ClipData.Item contactList = data.getItemAt(0);
            Pattern p = Pattern.compile("\\n[\\n]+");
            //fiecare element va fi un contact(nume+numar)
            String[] contacte = p.split(contactList.toString());
            int i;
            ArrayList<String> contactsToBeSaved = new ArrayList<>();
            for (i = 0; i < contacte.length; i++) {
                contactsToBeSaved.add(contacte[i]);
            }
            for(String con:contactsToBeSaved) {
                if (!con.matches(".*Name:.*\nNumber:.*")) {
                    Toast.makeText(this, "You don't have any contact in clip board", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    //salvarea contactelor
                    if(saveContacts(contactsToBeSaved))
                    {//afisarea confirmarii faptului ca au fost salvate contactele
                        Intent intent = new Intent(this, EndActivity.class);
                        intent.putExtra("text", "Contact(s) Saved");
                        intent.putExtra("finalContacts", contactsToBeSaved);
                        startActivity(intent);
                    }
                }
            }
        }catch (Exception e) {
            Toast.makeText(this,"No input.Please copy the contacts in clipboard",Toast.LENGTH_SHORT).show();
        }
    }


    //metoda care salveaa contactele in telefon
    public boolean saveContacts(ArrayList<String> contacts){
        boolean contactSaved = true;
        int i;
        for(i=0;i<contacts.size();i++) {
            //separarea numelui de numar
            String cname = getName(contacts.get(i).toString());;
            String cnumber= getNumber(contacts.get(i).toString());

            ArrayList<ContentProviderOperation> cnt = new ArrayList<ContentProviderOperation>();

            cnt.add(ContentProviderOperation.newInsert(
                    ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());

            //Completare nume
            if (cname != null) {
                cnt.add(ContentProviderOperation.newInsert(
                        ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(
                                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                cname).build());
            }

            //Completare numar
            if (cnumber != null) {
                cnumber.replace("\\s+","");
                cnt.add(ContentProviderOperation.
                        newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, cnumber)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());
            }

            // Adaugare contact daca avem drept pe lista de contacte
            try {
                getContentResolver().applyBatch(ContactsContract.AUTHORITY, cnt);
            } catch (Exception e) {
                e.printStackTrace();
                contactSaved=false;
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        return contactSaved;
    }

    private String getName (String s) {
        String search = "Name: ";
        return s.substring(s.indexOf(search) + search.length() , s.indexOf("\n"));
    }

    private String getNumber (String s) {
        String search = "Number: ";
        return s.substring(s.indexOf(search) + search.length() +1);
    }
}
