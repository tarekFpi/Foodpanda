package com.example.myfoodpanda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

public class UserCart_orderShow extends AppCompatActivity {
private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart_order_show);

        frameLayout=(FrameLayout)findViewById(R.id.userCartOrder);
       getSupportFragmentManager().beginTransaction().replace(R.id.userCartOrder,new cartOrder_fragment()).commit();
    }
}