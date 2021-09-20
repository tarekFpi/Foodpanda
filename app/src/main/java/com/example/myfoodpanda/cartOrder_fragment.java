package com.example.myfoodpanda;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.myfoodpanda.Model.CartOrder_Adapter;
import com.example.myfoodpanda.Model.userCartModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.hoang8f.widget.FButton;

public class cartOrder_fragment extends Fragment {
   private RecyclerView recyclerView;
    private  String serial_id,invoice_id,AreaName,category_name,RestName,subMenu,productName,delivary_time,delivary_free,user_gmail;
    private  int price=0,discount=0,quantity;
  private FButton fButton_update,fButton_verify;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
     private List<userCartModel>userCartModelList=new ArrayList<>();
      private CartOrder_Adapter adapter;
 private LayoutInflater layoutInflater;
 private View view_layout;
private SharedPreferences sharedPreferences_gamil;
    private SharedPreferences sharedPreferences_total;
private String UserGamil="";
private TextView textView_total_price;
//
    private String mParam1;
    private String mParam2;


  public LinearLayout linearLayout;
public   int totalPrice=0;
 public String status="";

    public cartOrder_fragment() {
        // Required empty public constructor
    }


    public static cartOrder_fragment newInstance(String param1, String param2) {
        cartOrder_fragment fragment = new cartOrder_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
       Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cart_order_fragment, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.cartOrder_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);
       fButton_verify=(FButton)view.findViewById(R.id.verify_adddress);
        textView_total_price=(TextView)view.findViewById(R.id.cart_total_price);
       linearLayout=(LinearLayout)view.findViewById(R.id.layout_userVerify);
       Toolbar  toolbar=(Toolbar)view.findViewById(R.id.toolbar_Id3);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#ff0090"));

        sharedPreferences_gamil=getContext().getSharedPreferences("com.example.myfoodpanda", Context.MODE_PRIVATE);
        UserGamil= sharedPreferences_gamil.getString("userGmail","user Gamil NOt Found");


        ProgressDialog progressdialog = new ProgressDialog(getContext());
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();
        progressdialog.setCancelable(false);

        StringRequest request=new StringRequest(Request.Method.POST,"https://myfoodpandaproject.000webhostapp.com/user_cart_orderShow.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                   if(response.contains("Data not found")){
                        linearLayout.setVisibility(View.GONE);
                        progressdialog.dismiss();
                    }

                    JSONArray jsonArray=new JSONArray(response);
                    userCartModelList.clear();
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        serial_id= jsonObject.getString("serial_no");
                        invoice_id= jsonObject.getString("invoices_id");
                        AreaName= jsonObject.getString("Area_Name");
                        category_name=  jsonObject.getString("category_name");
                        RestName=  jsonObject.getString("RestName");
                        subMenu= jsonObject.getString("sub_menu");
                        productName= jsonObject.getString("product_name");
                        price= jsonObject.getInt("sell_price");
                        discount=  jsonObject.getInt("discount");
                        quantity=jsonObject.getInt("quantity");
                        delivary_free= jsonObject.getString("Delivery_free");
                        delivary_time=  jsonObject.getString("Delivary_time");
                        user_gmail=  jsonObject.getString("userGmail");

  userCartModel getItem=
  new userCartModel(serial_id,invoice_id,AreaName,category_name,RestName,subMenu,productName,price,quantity,discount,delivary_free,delivary_time,user_gmail);
     userCartModelList.add(getItem);
       totalPrice=getItem.getSell_price()*getItem.getQuantity()+totalPrice-discount;
    textView_total_price.setText("Total price:"+totalPrice);
  adapter=new CartOrder_Adapter(getContext(),userCartModelList);
  recyclerView.setAdapter(adapter);
  progressdialog.dismiss();

    }

                    adapter.setOnItemClick(new CartOrder_Adapter.onItemClickLisiner() {
                        @Override
                        public void OnClick_Lisiner(int position) {


                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>hasmp_gmail=new HashMap<String,String>();
                hasmp_gmail.put("userGamil",UserGamil);
                return hasmp_gmail;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);

        fButton_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cart_totalPrice();
                StringRequest string_details=new StringRequest(Request.Method.POST,"https://myfoodpandaproject.000webhostapp.com/user_information_Check.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("1")){
                            Intent intent=new Intent(getContext(),UserAll_Order.class);
                            startActivity(intent);
                        }else{
                            Intent intent=new Intent(getContext(),User_AddressAdd.class);
                            startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){

                    @Override
                    protected Map<String, String> getParams()  {

                        Map<String,String>hasmp_check=new HashMap<String,String>();
                        hasmp_check.put("userGamil",UserGamil);
                        return  hasmp_check;
                    }
                };
                RequestQueue requestQueue_details= Volley.newRequestQueue(getContext());
                requestQueue_details.add(string_details);
            }
        });

        return  view;
    }

  public void Cart_totalPrice(){
     StringRequest stringRequest_totalprice=new StringRequest(Request.Method.POST, "https://myfoodpandaproject.000webhostapp.com/user_cart_orderShow.php", new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {

             try {
                 JSONArray jsonArray= new JSONArray(response);
                 userCartModelList.clear();
                 for (int i = 0; i <jsonArray.length() ; i++) {
                     JSONObject jsonObject = jsonArray.getJSONObject(i);
                     price = jsonObject.getInt("sell_price");
                     discount = jsonObject.getInt("discount");
                     quantity = jsonObject.getInt("quantity");

                     totalPrice =price * quantity+ totalPrice-quantity;
                     textView_total_price.setText("Total price:" + totalPrice);
                 }

             } catch (JSONException e) {
                 e.printStackTrace();
             }
         }
     }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {

         }
     });

      RequestQueue requenstQueue_total=Volley.newRequestQueue(getContext());

      requenstQueue_total.add(stringRequest_totalprice);
  }
}