package com.example.fabian.chsapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class GenerateQrActivity extends AppCompatActivity {

    ArrayList<String> contacts;
    ImageView imgView;
    String contacte = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);
        Intent intent = getIntent();
        //se preiau contactele ce vor fi trimise trimis
        contacts = intent.getStringArrayListExtra("contacts");
        imgView = findViewById(R.id.imageView);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            //formam textul ce va fi continut de codul qr
            for(int i = 0 ; i < contacts.size() ; i++) {
                int lastSpace = contacts.get(i).lastIndexOf(' ' );
                contacte+= "Name: " + contacts.get(i).substring(0, lastSpace) + "\n";
                contacte+= "Number: " + contacts.get(i).substring(lastSpace) + "\n";
                contacte+="\n";
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
