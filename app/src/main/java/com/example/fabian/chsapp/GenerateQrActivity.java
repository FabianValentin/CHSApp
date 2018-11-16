package com.example.fabian.chsapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    public static final int REQUEST_CODE = 1;
    TextView contactName;
    TextView contactNumber;
    Button gen_button;
    Button search_button;
    ImageView imgView;
    String textName;
    String textNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        contactName = findViewById(R.id.contactName);
        contactNumber = findViewById(R.id.contactNumber);
        gen_button = findViewById(R.id.GenerateButton);
        search_button = findViewById(R.id.contactSearch);
        imgView = findViewById(R.id.imageView);
        gen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    textName = contactName.getText().toString().trim();
                    textNumber = contactNumber.getText().toString().trim();
                    BitMatrix bitMatrix = multiFormatWriter.encode(textName+"\n"+textNumber, BarcodeFormat.QR_CODE, 200,200);
                    BarcodeEncoder barcodeDetector = new BarcodeEncoder();
                    Bitmap bitmap = barcodeDetector.createBitmap(bitMatrix);
                    imgView.setImageBitmap(bitmap);
                }catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ChooseContactActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case REQUEST_CODE:
                if(resultCode == Activity.RESULT_OK){
                    String name = data.getStringExtra("contactName");
                    String number = data.getStringExtra("contactNumber");
                    contactName.setText(name);
                    contactNumber.setText(number);

                    gen_button.setVisibility(View.VISIBLE);
                }
        }
    }
}
