package com.example.myfoodpanda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class User_AddressAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__address_add);
       // getSupportActionBar().setHomeButtonEnabled(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.user_addFrameLayout,new UserAddress_Fragment()).commit();

    }
}