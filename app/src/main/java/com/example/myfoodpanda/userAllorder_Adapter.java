package com.example.myfoodpanda;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodpanda.Model.userAll_order_model;

import java.util.List;

public class userAllorder_Adapter extends RecyclerView.Adapter<userAllorder_Adapter.MyviewHolder> {
  private Context context;
  private List<userAll_order_model>userAll_order_models;
  private OItemClickLisiner clickLisiner;
    private static int listposition=-1;
    public userAllorder_Adapter(Context context, List<userAll_order_model> userAll_order_models) {
        this.context = context;
        this.userAll_order_models = userAll_order_models;
    }

    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater layoutInflater=LayoutInflater.from(context);
       View view= layoutInflater.inflate(R.layout.user_all_order,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {

        userAll_order_model item=userAll_order_models.get(position);

        myviewHolder.textView_areaName.setText("Area:"+item.getAreaName());
        myviewHolder.textView_restName.setText("Restadunt Name:"+item.getRestName());
        myviewHolder.textView_productName.setText("Name:"+item.getProduct_Name());
        myviewHolder.textView_quantity.setText("Quantity:"+item.getQuantity());
        myviewHolder.textView_delivary_free.setText("Delivary Free:"+item.getDelivary_free());
        myviewHolder.textView_userName.setText("User Name:"+item.getUserName());
        myviewHolder.textView_gmail.setText("Gmail:"+item.getUser_gmail());
        myviewHolder.textView_city.setText("City:"+item.getCity());
        myviewHolder.textView_userArea.setText("Area:"+item.getUserArea());
        myviewHolder.textView_float_id.setText("Flat No:"+item.getFlat_on());
        myviewHolder.textView_phone.setText("Phone:"+item.getPhone());
        myviewHolder.textView_sellprice.setText("price:"+item.getSell_price());
        myviewHolder.textView_discount.setText("Discount:"+item.getDiscount());

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
        return userAll_order_models.size();
    }

    class  MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
   private TextView textView_areaName,textView_restName,textView_productName,textView_delivary_free,textView_quantity,
        textView_userName,textView_gmail,textView_location,textView_userArea,textView_float_id,
           textView_city,textView_phone,textView_sellprice,textView_discount;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            textView_areaName=(TextView)itemView.findViewById(R.id.cartf_area_Name);
            textView_restName=(TextView)itemView.findViewById(R.id.cart_fFrestardent_Name);
            textView_productName=(TextView)itemView.findViewById(R.id.cartf_product_Name);
            textView_delivary_free=(TextView)itemView.findViewById(R.id.cartf_delivary_free);
            textView_quantity=(TextView)itemView.findViewById(R.id.cartf_quanitiy);
            textView_userName=(TextView)itemView.findViewById(R.id.fuser_name);
            textView_gmail=(TextView)itemView.findViewById(R.id.fgmail);
           // textView_location=(TextView)itemView.findViewById(R.id.flocation);
            textView_userArea=(TextView)itemView.findViewById(R.id.fArea);
            textView_float_id=(TextView)itemView.findViewById(R.id.fFloat_id);
            textView_city=(TextView)itemView.findViewById(R.id.fcity);
            textView_phone=(TextView)itemView.findViewById(R.id.fphone);


            textView_sellprice=(TextView)itemView.findViewById(R.id.cartf_sellPrice);
            textView_discount=(TextView)itemView.findViewById(R.id.cartf_discount);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            clickLisiner.OnClickLisiner(position);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Do your Any Action");
            MenuItem menuIte_Update=menu.add(Menu.NONE,1,1,"Delete");
            MenuItem menuItem_process_pay=menu.add(Menu.NONE,2,2,"process To pay");
            menuIte_Update.setOnMenuItemClickListener(this);
            menuItem_process_pay.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position=getAdapterPosition();

            switch(item.getItemId()){
                case 1:
                    clickLisiner.OnDelete(position);
                    break;

                case 2:
                    clickLisiner.proces_toPay(position);
                    break;
            }
            return true;
        }
    }

    interface OItemClickLisiner{
      void OnClickLisiner(int position);
        void OnDelete(int position);
        void proces_toPay(int position);
    }

    public  void setonItemClickLisiner(OItemClickLisiner clickLisiner){
       this.clickLisiner=clickLisiner;
    }
}
