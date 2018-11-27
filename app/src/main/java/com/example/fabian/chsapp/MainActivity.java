package com.example.fabian.chsapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

    }

    public void add(View view) {

        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }

    public void send(View view) {

        Intent intent = new Intent(this, ChooseContactActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(!hasPermissions(this,PERMISSIONS)){
                        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
                    }
                } else {
                    boolean camera = false;
                    boolean readC = false;
                    boolean writeC = false;
                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        camera = true;
                    }
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                        readC = true;
                    }
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                        writeC = true;
                    }
                    int i;
                    for(i=0;i<PERMISSIONS.length;i++)
                    {
                        if(camera == true && PERMISSIONS[i].equals(android.Manifest.permission.CAMERA)){
                            List<String> list = new ArrayList<String>(Arrays.asList(PERMISSIONS));
                            list.remove(PERMISSIONS[i]);
                            PERMISSIONS = list.toArray(new String[0]);
                        }
                        if(readC == true && PERMISSIONS[i].equals(android.Manifest.permission.READ_CONTACTS)){
                            List<String> list = new ArrayList<String>(Arrays.asList(PERMISSIONS));
                            list.remove(PERMISSIONS[i]);
                            PERMISSIONS = list.toArray(new String[0]);
                        }
                        if(writeC == true && PERMISSIONS[i].equals(android.Manifest.permission.WRITE_CONTACTS)){
                            List<String> list = new ArrayList<String>(Arrays.asList(PERMISSIONS));
                            list.remove(PERMISSIONS[i]);
                            PERMISSIONS = list.toArray(new String[0]);
                        }
                    }
                    if(!hasPermissions(this,PERMISSIONS))
                        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
                }
                break;
            }

        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
