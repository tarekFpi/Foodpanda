package com.example.myfoodpanda.Model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.myfoodpanda.R;
import com.example.myfoodpanda.User_AddressAdd;
import com.example.myfoodpanda.cartOrder_fragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialContainerTransform;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.hoang8f.widget.FButton;

public class CartOrder_Adapter extends RecyclerView.Adapter<CartOrder_Adapter.MyviewHolder> {
    private Context context;
    private List<userCartModel>userCartModelList;
   private  onItemClickLisiner clickLisiner;
    private LayoutInflater layoutInflater;
    private View view_layout;
    private FButton fButton_update;
    private   int ptotal=0;
    private static int listposition=-1;
    private ElegantNumberButton numberButton;
    private TextView textView_productName,textView_quantity;
  private cartOrder_fragment st;
    public CartOrder_Adapter(Context context, List<userCartModel> userCartModelList) {
        this.context = context;
        this.userCartModelList = userCartModelList;
    }

    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater=LayoutInflater.from(context);
      View view= layoutInflater.inflate(R.layout.cart_order_layout,parent,false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {
 SharedPreferences shared_total=context.getSharedPreferences("com.example.myfoodpanda", Context.MODE_PRIVATE);
        userCartModel item= userCartModelList.get(position);

        myviewHolder.textView_areaName.setText("Area:"+item.getAreaName());
        myviewHolder.textView_restName.setText("Restaurant:"+item.getRestName());
        myviewHolder.textView_delivary_free.setText("Area"+item.getDelivary_free());
        myviewHolder.textView_pdName.setText("Name:"+item.getProduct_Name());
        myviewHolder.textView_quanitiy.setText("Quanitiy:"+item.getQuantity());
        myviewHolder.textView_discount.setText("Discount:"+item.getDiscount());
        myviewHolder.textView_sellPrice.setText("price:"+item.getSell_price());


        myviewHolder.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userCartModel get_item=userCartModelList.get(position);
                StringRequest string_delete=new StringRequest(Request.Method.POST, "https://myfoodpandaproject.000webhostapp.com/user_card_OrderDelete.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                 Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                        userCartModelList.remove(position);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){

                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String,String>hasmp_delete=new HashMap<String,String>();
                        hasmp_delete.put("serial_id",get_item.getSerial_id());
                        return  hasmp_delete;
                    }
                };
                RequestQueue requestQueue_delete= Volley.newRequestQueue(context);
                requestQueue_delete.add(string_delete);
            }
        });


  myviewHolder.floatingActionButton_updateQuantity.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

          BottomSheetDialog sheetDialog=new BottomSheetDialog(context);
         // layoutInflater=getLayoutInflater();
          layoutInflater=LayoutInflater.from(context);
          view_layout= layoutInflater.inflate(R.layout.care_order_update,null);

          textView_productName=(TextView)view_layout.findViewById(R.id.cartUp_pdName);
          textView_quantity=(TextView)view_layout.findViewById(R.id.cateUp_quaitiy);

          textView_productName.setText("Name:"+item.getProduct_Name());
          textView_quantity.setText("Quantity:"+item.getQuantity());
          fButton_update=(FButton)view_layout.findViewById(R.id.update_tbtn);
          numberButton=(ElegantNumberButton)view_layout.findViewById(R.id.upudate_Quantity);
          sheetDialog.setContentView(view_layout);

          fButton_update.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  userCartModel item_get= userCartModelList.get(position);

                  String Quanitiy=numberButton.getNumber().toString();
                  String serial_id=item_get.getSerial_id();

                  StringRequest stringUpdate=new StringRequest(Request.Method.POST, "https://myfoodpandaproject.000webhostapp.com/userOrder_Update.php", new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          Toast.makeText(context, response, Toast.LENGTH_SHORT).show();

                        userCartModelList.clear();
                         // notifyDataSetChanged();
                         // notifyDataSetChanged();
                      }
                  }, new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                          Toast.makeText(context, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                      }
                  }){
                      @Override
                      protected Map<String, String> getParams()  {
                          Map<String,String>hasmp_data=new HashMap<String,String>();
                          hasmp_data.put("serial_id",serial_id);
                          hasmp_data.put("quanitiy_up",Quanitiy);
                          return hasmp_data;
                      }
                  };
                  RequestQueue request1=Volley.newRequestQueue(context);
                  request1.add(stringUpdate);

              }
          });
          sheetDialog.show();
         // notifyItemChanged(position);

      }
  });
        setAnimiton(myviewHolder.itemView,position);
    }

    void setAnimiton(View viewAnimition,int position) {
        if (position > listposition) {
            ScaleAnimation animation = new ScaleAnimation(0.0f, 0.1f, 0.0f, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(2000);
            viewAnimition.startAnimation(animation);
            listposition = position;
        }
    }
    @Override
    public int getItemCount() {
        return userCartModelList.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

      private TextView textView_areaName,textView_restName,
  textView_pdName,textView_sellPrice,textView_quanitiy,textView_discount,
              textView_delivary_free,
              textView_userAreaName,textView_float,textView_city,textView_userName,
              textView_phone;
  private FloatingActionButton  floatingActionButton,floatingActionButton_updateQuantity;

  private MaterialCardView cardView;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            textView_areaName=(TextView)itemView.findViewById(R.id.cart_area_Name);
            textView_restName=(TextView)itemView.findViewById(R.id.restardent_Name);
            textView_pdName=(TextView)itemView.findViewById(R.id.cart_product_Name);
            textView_sellPrice=(TextView)itemView.findViewById(R.id.cart_sellPrice);
            textView_quanitiy=(TextView)itemView.findViewById(R.id.cart_quanitiy);

            textView_discount=(TextView)itemView.findViewById(R.id.cart_discount);
            textView_delivary_free=(TextView)itemView.findViewById(R.id.cart_delivary_free);
             floatingActionButton=(FloatingActionButton)itemView.findViewById(R.id.floation_delete);
            floatingActionButton_updateQuantity=(FloatingActionButton)itemView.findViewById(R.id.floation_update);
/*
            textView_userAreaName=(TextView)itemView.findViewById(R.id.cart_delivary_free);
            textView_float=(TextView)itemView.findViewById(R.id.cart_delivary_free);
            textView_city=(TextView)itemView.findViewById(R.id.cart_delivary_free);
            textView_city=(TextView)itemView.findViewById(R.id.cart_delivary_free);*/

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            clickLisiner.OnClick_Lisiner(position);
        }
    }

    public interface  onItemClickLisiner{
        void OnClick_Lisiner(int position);
    }

    public  void setOnItemClick(onItemClickLisiner clickLisiner){
        this.clickLisiner=clickLisiner;
    }
}
