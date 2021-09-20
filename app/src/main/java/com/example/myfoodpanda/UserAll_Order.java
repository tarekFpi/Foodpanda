package com.example.myfoodpanda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserAll_Order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_all__order);

   getSupportFragmentManager().beginTransaction().replace(R.id.user_allOrderFremeLayout,new userAllOrder_Fragment()).commit();

    }
}