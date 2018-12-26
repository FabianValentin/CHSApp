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
    ArrayList<Contact> encodedContacts;
    ImageView imgView;
    String contacte = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);
        Intent intent = getIntent();
        //se preiau contactele ce vor fi trimise trimis
        contacts = (ArrayList<Contact>)intent.getSerializableExtra("contacts");
        encodedContacts = contacts;
        imgView = findViewById(R.id.imageView);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            //formam textul ce va fi continut de codul qr
            for(int i = 0 ; i < encodedContacts.size() ; i++) {
                encodedContacts.get(i).setName(URLEncoder.encode(encodedContacts.get(i).getName()));
                contacte += encodedContacts.get(i);
            }
            //criptam contactele in format special pt qr
            BitMatrix bitMatrix = multiFormatWriter.encode(contacte, BarcodeFormat.QR_CODE, 200,200);
            BarcodeEncoder barcodeDetector = new BarcodeEncoder();
            Bitmap bitmap = barcodeDetector.createBitmap(bitMatrix);
            //afisam qr-ul
            imgView.setImageBitmap(bitmap);
            imgView.setVisibility(View.VISIBLE);
        }catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
