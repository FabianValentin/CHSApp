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

public class GenerateQrActivity extends AppCompatActivity {

    String name;
    String number;

    Button send_contact;

    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);
        Intent intent = getIntent();

        name = "Fabian";//intent.getStringExtra("contactName");
        number = "000";//intent.getStringExtra("contactNumber");
        send_contact = findViewById(R.id.sendContact);
        imgView = findViewById(R.id.imageView);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(name+"\n"+number, BarcodeFormat.QR_CODE, 200,200);
            BarcodeEncoder barcodeDetector = new BarcodeEncoder();
            Bitmap bitmap = barcodeDetector.createBitmap(bitMatrix);
            imgView.setImageBitmap(bitmap);
            imgView.setVisibility(View.VISIBLE);
        }catch (WriterException e) {
            e.printStackTrace();
        }
        send_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

       /* search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ChooseContactActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });*/

    }
}
