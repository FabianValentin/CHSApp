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

        //se vor cere permisii pentru camera si lista de contacte
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

    }

    //aplicatia este separata in 2: partea de adaugare si partea de trimitere a contactelor
    public void add(View view) {
        //se va deschide o pagina cu cele 2 optiune prin care se poate adauga un contact
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }

    public void send(View view) {
        //se va deschide o lista cu toate contactele pentru a permite utilizatorului sa le selecteze pe cele dorite
        Intent intent = new Intent(this, ChooseContactActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(!hasPermissions(this,PERMISSIONS)){
                        //cererea de permisie
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
                    //verificam ce permisii exista deja si le eliminam din lista
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
                    //recursiv, in caz ca utilizatorul a apasat din greseala 'Refuz'
                    if(!hasPermissions(this,PERMISSIONS))
                        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
                }
                break;
            }

        }
    }

    //se verifica permisia pentru camera si contacte(citire si scriere)
    //daca nu este acordata permisie pentru cel putin una dintre acestea aplicatia o va cere
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
