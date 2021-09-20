package com.example.myfoodpanda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        String dateString = dateFormat.format(new Date()).toString();


 //Displaying current date and time in 12 hour format with AM/PM
        DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
        Toast.makeText(this, "data:"+dateFormat2, Toast.LENGTH_LONG).show();

    }
}