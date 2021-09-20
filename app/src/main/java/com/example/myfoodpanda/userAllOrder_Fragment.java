package com.example.myfoodpanda;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfoodpanda.Model.userAll_order_model;
import com.example.myfoodpanda.Model.userCartModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.hoang8f.widget.FButton;

public class userAllOrder_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private  String serial_id,invoice_id,AreaName,category_name,
            RestName,subMenu,productName,delivary_time,delivary_free,user_gmail,userLaction,userCity,
            user_area,user_float_on,user_phone,userName;
    private TextView textView_totalprice;
    private FButton fButton_process,fButton_voucher;
private userAllorder_Adapter adapter;
private Spinner spinner;
private LayoutInflater layoutInflater;
private View view_layout;
private TextView textView_admin_phon;
private EditText editText_phone,editText_transactionid;
    private EditText editText_phone_voucher,editText_transactionid_voucher,editText_voucher_cod;
private List<userAll_order_model>modelList= new ArrayList<>();
   private int total_price=0;
    private ArrayList<String>arrayList_payment=new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter_payment;
    String admin_phone="";
    private  int price=0,discount=0,quantity,Total_price=0;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
   private SharedPreferences sharedPreferences_gamil;
   private String UserGamil="";
    private String mParam1;
    private String mParam2;

    public userAllOrder_Fragment() {

    }

    public static userAllOrder_Fragment newInstance(String param1, String param2) {
        userAllOrder_Fragment fragment = new userAllOrder_Fragment();
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
            Toast.makeText(getContext(), "Oncreate", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
               Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_all_order_, container, false);
           recyclerView=(RecyclerView)view.findViewById(R.id.AllOrder_recylerviw);
           recyclerView.setHasFixedSize(true);
           recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProgressDialog progressdialog = new ProgressDialog(getContext());
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();
        progressdialog.setCancelable(false);

        sharedPreferences_gamil=getContext().getSharedPreferences("com.example.myfoodpanda", Context.MODE_PRIVATE);
        UserGamil= sharedPreferences_gamil.getString("userGmail","user Gamil NOt Found");

        Toolbar toolbar=(Toolbar)view.findViewById(R.id.toolbar_Id4);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#ff0090"));

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://myfoodpandaproject.000webhostapp.com/userAll_orderShow.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(response.contains("Data not found")){
                      progressdialog.dismiss();
                    }
                    JSONArray jsonArray=new JSONArray(response);
                    modelList.clear();
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
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

                        userName=  jsonObject.getString("User_name");
                        user_area=  jsonObject.getString("Aare_name");
                        user_float_on= jsonObject.getString("flat_no");
                        userCity=  jsonObject.getString("city_twon");
                        user_phone=  jsonObject.getString("phone_number");

           total_price=price*quantity+total_price;
      userAll_order_model Item=new
     userAll_order_model(serial_id,invoice_id,AreaName,category_name,RestName,subMenu,
   productName,price,quantity,discount,delivary_time,delivary_free,user_gmail,userName,user_area,user_float_on,userCity,user_phone,total_price);
       modelList.add(Item);

                    }
    adapter=new userAllorder_Adapter(getContext(),modelList);
    recyclerView.setAdapter(adapter);
    progressdialog.dismiss();
     adapter.notifyDataSetChanged();
    adapter.setonItemClickLisiner(new userAllorder_Adapter.OItemClickLisiner() {
        @Override
        public void OnClickLisiner(int position) {
            userAll_order_model getPosition=modelList.get(position);

            LayoutInflater layoutInflater=getLayoutInflater();
            EditText editText_Update_phone,editText_areaUpdate,editText_flat_id;
            View view_layout;

            BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(getContext());
            view_layout=layoutInflater.inflate(R.layout.user_deatils_update,null);
             bottomSheetDialog.setContentView(view_layout);

            editText_Update_phone=(EditText)view_layout.findViewById(R.id.editUpdate_userPhone);
            editText_areaUpdate=(EditText)view_layout.findViewById(R.id.edit_update_area);
            editText_flat_id=(EditText)view_layout.findViewById(R.id.edit_update_flat_id);
            FButton update_btn=(FButton)view_layout.findViewById(R.id.update_btn);

            editText_Update_phone.setText(getPosition.getPhone());
            editText_areaUpdate.setText(getPosition.getAreaName());
            editText_flat_id.setText(getPosition.getFlat_on());

            update_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String user_gamil=getPosition.getUser_gmail();
                    String user_area=editText_areaUpdate.getText().toString();
                    String user_flat=editText_flat_id.getText().toString();
                    String user_phone=editText_Update_phone.getText().toString();

                    if(editText_areaUpdate.getText().toString().isEmpty()){
                        editText_areaUpdate.setError("Your Area Name Empty..");
                        editText_areaUpdate.requestFocus();
                    }else  if(editText_flat_id.getText().toString().isEmpty()){
                        editText_flat_id.setError("Your Flat,Number is Empty..");
                        editText_flat_id.requestFocus();
                    }else  if(editText_Update_phone.getText().toString().isEmpty()){
                        editText_Update_phone.setError("Your Phone Number is Empty..");
                        editText_Update_phone.requestFocus();
                    }else {
                        StringRequest string_update=new StringRequest(Request.Method.POST, "https://myfoodpandaproject.000webhostapp.com/details_update.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                                adapter.notifyDataSetChanged();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){

                            @Override
                            protected Map<String, String> getParams()  {
                                Map<String,String>hasmp_update=new HashMap<String,String>();
                                hasmp_update.put("userGmail",user_gamil);
                                hasmp_update.put("AreaName",user_area);
                                hasmp_update.put("phone",user_phone);
                                hasmp_update.put("flat_on",user_flat);
                                return  hasmp_update;
                            }
                        };
                        RequestQueue requestQueue_update=Volley.newRequestQueue(getContext());
                        requestQueue_update.add(string_update);
                    }


                }
            });

             bottomSheetDialog.show();
        }

        @Override
        public void OnDelete(int position) {
            userAll_order_model getItem=modelList.get(position);

           StringRequest string_delete=new StringRequest(Request.Method.POST, "https://myfoodpandaproject.000webhostapp.com/user_card_OrderDelete.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
             Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
             modelList.remove(position);
             adapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){

                @Override
                protected Map<String, String> getParams()  {
                    Map<String,String>hasmp_update=new HashMap<String,String>();
                    hasmp_update.put("serial_id",getItem.getSerial_id());
                    return  hasmp_update;
                }
            };
            RequestQueue requestQueue_delete=Volley.newRequestQueue(getContext());
            requestQueue_delete.add(string_delete);

        }

        @Override
        public void proces_toPay(int position) {
            userAll_order_model Position_order=modelList.get(position);

            BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(getContext());
            layoutInflater=getLayoutInflater();
            view_layout= layoutInflater.inflate(R.layout.payment_layout,null);
            editText_phone=(EditText)view_layout.findViewById(R.id.edit_userPhone);
            editText_transactionid=(EditText)view_layout.findViewById(R.id.edit_transaction_id);
            textView_admin_phon=(TextView)view_layout.findViewById(R.id.admin_phone);
            spinner=(Spinner)view_layout.findViewById(R.id.spinner_payment);
            FButton fButton_checkout=(FButton)view_layout.findViewById(R.id.check_out_btn);
            textView_totalprice=(TextView)view_layout.findViewById(R.id.text_totalprice);
            textView_totalprice.setText("Total price:"+Position_order.getTotal_price());

            StringRequest stringRequest1=new StringRequest(Request.Method.POST, "https://myfoodpandaproject.000webhostapp.com/getpayment_mathod.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray   jsonArray_payment_mathod = new JSONArray(response);
                        arrayList_payment.clear();
                        arrayList_payment.add("Select---Payment--Mathod");
                        for(int i=0; i<jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray_payment_mathod.getJSONObject(i);
                            arrayList_payment.add(jsonObject.getString("Name").toString());
                        }
                        arrayAdapter_payment=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,arrayList_payment);
                        spinner.setAdapter(arrayAdapter_payment);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String getbkashName=arrayList_payment.get(position).toString();

                                StringRequest stringReq_getphone=new StringRequest(Request.Method.POST, "https://myfoodpandaproject.000webhostapp.com/getpayment_phone.php", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONArray   jsonArray_getphone = new JSONArray(response);
                                            for(int i=0; i<jsonArray.length();i++) {
                                                JSONObject jsonObject = jsonArray_getphone.getJSONObject(i);
                                                textView_admin_phon.setText("Bkash:"+jsonObject.getString("phone_number"));
                                                admin_phone=jsonObject.getString("phone_number");
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
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
                                        Map<String,String>hasmp_data=new HashMap<String,String>();
                                        hasmp_data.put("name",getbkashName);
                                        return hasmp_data;
                                    }
                                };
                                RequestQueue request_getPhone=Volley.newRequestQueue(getContext());
                                request_getPhone.add(stringReq_getphone);
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue request_payment=Volley.newRequestQueue(getContext());
            request_payment.add(stringRequest1);



            fButton_checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

          String bkash_transction_id=editText_transactionid.getText().toString();
              String user_bkash_phone=editText_phone.getText().toString();

                    SimpleDateFormat sm=new SimpleDateFormat("yyyy/MM/dd");
                    String Order_Date= sm.format(new Date());

                    if(editText_phone.getText().toString().isEmpty()){
                        editText_phone.setError("Your Bkash Phone is Empty..");
                        editText_phone.requestFocus();
                    }else  if(editText_transactionid.getText().toString().isEmpty()){
                        editText_transactionid.setError("Your Bkash Transction Id is Empty..");
                        editText_transactionid.requestFocus();
                    }else if(spinner.getSelectedItem().toString().contains("Select---Payment--Mathod")){
                        Toast.makeText(getContext(), "Select---Payment--Mathod", Toast.LENGTH_SHORT).show();
                        spinner.requestFocus();
                    }else{

                        StringRequest string_order_process=new StringRequest(Request.Method.POST, "https://myfoodpandaproject.000webhostapp.com/sell_Order.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                                Notification_Add();
                            }
                        },new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() {

                                Map<String,String>hasmp_order=new HashMap<String,String>();
                                hasmp_order.put("invoice_Id",Position_order.getInvoie_id());
                                hasmp_order.put("areaName",Position_order.getAreaName());
                                hasmp_order.put("cateogryName",Position_order.getCategoryName());
                                hasmp_order.put("restName",Position_order.getRestName());
                                hasmp_order.put("sub_meu",Position_order.getSub_menu());
                                hasmp_order.put("product_name",Position_order.getProduct_Name());
                                hasmp_order.put("sell_price",String.valueOf(Position_order.getSell_price()));
                                hasmp_order.put("sell_quanitiy",String.valueOf(Position_order.getQuantity()));
                                hasmp_order.put("sell_discount",String.valueOf(Position_order.getDiscount()));
                                hasmp_order.put("delivary_free",String.valueOf(Position_order.getDelivary_free()));
                                hasmp_order.put("user_Gamil",Position_order.getUser_gmail());
                                hasmp_order.put("userName",Position_order.getUserName());
                                hasmp_order.put("userArea",Position_order.getUserArea());
                                hasmp_order.put("float_id",Position_order.getFlat_on());
                                hasmp_order.put("city",Position_order.getCity());
                                hasmp_order.put("phone",Position_order.getPhone());
                                hasmp_order.put("total_price",String.valueOf(Position_order.getTotal_price()));
                                hasmp_order.put("order_date", Order_Date);
                                hasmp_order.put("andmin_phone",admin_phone);
                                hasmp_order.put("payment_mahod", spinner.getSelectedItem().toString());
                                hasmp_order.put("user_bkash_phone",user_bkash_phone);
                                hasmp_order.put("trasnaction_Id", bkash_transction_id);
                                return hasmp_order;
                            }
                        };
                        RequestQueue requestQueue_update=Volley.newRequestQueue(getContext());
                        requestQueue_update.add(string_order_process);
                    }
                }
            });
            bottomSheetDialog.setContentView(view_layout);
            bottomSheetDialog.show();

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
                hasmp_gmail.put("UserGamil",UserGamil);
                return hasmp_gmail;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        return  view;
    }

    private void Notification_Add(){

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel("Channel","Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=(NotificationManager)getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notification= new NotificationCompat.Builder(getActivity(),"Channel")
                .setContentTitle("Foodpanda Order..")
                .setSmallIcon(R.drawable.bell)
                .setContentText("Check Out SuessFulll..")
                .setAutoCancel(true)
                //.setSound(Uri.)
                .setWhen(System.currentTimeMillis());

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getContext());
        notificationManagerCompat.notify(1,notification.build());

    }

}