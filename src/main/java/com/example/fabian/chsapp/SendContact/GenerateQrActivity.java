package com.example.fabian.chsapp.SendContact;


import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fabian.chsapp.Contact;
import com.example.fabian.chsapp.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.net.URLEncoder;
import java.util.ArrayList;

public class GenerateQrActivity extends AppCompatActivity {

    ArrayList<Contact> contacts;
    ImageView imgView;
    StringBuilder structuredContacts = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);
        Intent intent = getIntent();
        //se preiau contactele ce vor fi trimise trimis
        contacts = (ArrayList<Contact>)intent.getSerializableExtra("contacts");
        imgView = findViewById(R.id.imageView);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            //formam textul ce va fi continut de codul qr
            for(int i = 0 ; i < contacts.size() ; i++) {
                contacts.get(i).setName(URLEncoder.encode(contacts.get(i).getName()));
                structuredContacts.append(contacts.get(i)+"\n\n");
            }
            //criptam contactele in format special pt qr
            BitMatrix bitMatrix = multiFormatWriter.encode(structuredContacts.toString(), BarcodeFormat.QR_CODE, 200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            //afisam qr-ul
            imgView.setImageBitmap(bitmap);
            imgView.setVisibility(View.VISIBLE);
        }catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
