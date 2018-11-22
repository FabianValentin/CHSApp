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
    public static final int REQUEST_CODE = 1;
    TextView contactName;
    TextView contactNumber;
    Button gen_button;
    Button search_button;
    Button send_contact;
    ImageView imgView;
    String textName;
    String textNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        contactName = findViewById(R.id.contactName);
        contactName.setText("Contact Name..");
        contactNumber = findViewById(R.id.contactNumber);
        contactNumber.setText("Contact Number..");
        gen_button = findViewById(R.id.GenerateButton);
        search_button = findViewById(R.id.contactSearch);
        send_contact = findViewById(R.id.sendContact);
        imgView = findViewById(R.id.imageView);
        imgView.setVisibility(View.INVISIBLE);
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
                    imgView.setVisibility(View.VISIBLE);
                }catch (WriterException e) {
                    e.printStackTrace();
                }
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

    public void startChooseContact(View view) {
        Intent intent = new Intent(view.getContext(), ChooseContactActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case REQUEST_CODE:
                if(resultCode == Activity.RESULT_OK){
                    send_contact.setVisibility(View.VISIBLE);
                    search_button.setVisibility(View.INVISIBLE);
                    name = data.getStringExtra("contactName");
                    number = data.getStringExtra("contactNumber");
                    contactName.setText(name);
                    contactNumber.setText(number);

                    gen_button.setVisibility(View.VISIBLE);
                }
        }
    }

    public void sendContact(View view) {
        Log.i("Send email", "");

        String[] TO = {"someone@gmail.com"};
        String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your contact");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Name: " + name + "\n" + "Number: " + number);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(GenerateQrActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
