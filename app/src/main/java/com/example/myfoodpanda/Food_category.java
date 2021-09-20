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
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.myfoodpanda.Model.CategoryAdapter;
import com.example.myfoodpanda.Model.Category_model;
import com.example.myfoodpanda.Model.ProductShow_Model;
import com.example.myfoodpanda.Model.Product_show_Adpter;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.hoang8f.widget.FButton;

public class Food_category extends Fragment {

 private RecyclerView recyclerView;
 private TextView textView_restDetails;
 private ImageSlider imageSlider;
private FButton fButton_add,fButton_cartViw;
private ImageView imageView_order;
    String areaName,categoryName,imageUrl,RestaurName,sub_menu,restDetails,delivary_time;
    private int delivary_free,discount=0;
private CategoryAdapter adpter;
private List<Category_model>category_modelList;
    private int sellPrice;
 private LayoutInflater layoutInflater;
 private View view_layout;
  private  String serial_id,sell_rice,productName,pd_deatils,image;
private TextView textView_view_recipName,textView_price,textView_delivaryFree,textView_delivaryTime,textView_countCart;
private ElegantNumberButton numberButton;
private RelativeLayout relativeLayout_cart;
 private String UserGamil="";
 private Toolbar toolbar;
 private String url_cart="https://myfoodpandaproject.000webhostapp.com/user_cart_orderShow.php";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
 private SharedPreferences sharedPreferences_gamil;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Food_category() {
   // Required empty public constructor
    }

     public Food_category(String areaName,String categoryName,String imageUrl,String RestaurName,String sub_menu,int delivary_free,String restDetails,String delivary_time) {
    this.areaName=areaName;
    this.categoryName=categoryName;
   this.imageUrl=imageUrl;
  this.RestaurName=RestaurName;
  this.sub_menu=sub_menu;
  this.delivary_free=delivary_free;
  this.restDetails=restDetails;
  this.delivary_time=delivary_time;

    }


    public static Food_category newInstance(String param1, String param2) {
        Food_category fragment = new Food_category();
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
        View view= inflater.inflate(R.layout.fragment_food_category, container, false);
        sharedPreferences_gamil=getContext().getSharedPreferences("com.example.myfoodpanda", Context.MODE_PRIVATE);
        UserGamil= sharedPreferences_gamil.getString("userGmail","user Gamil NOt Found");

         relativeLayout_cart=(RelativeLayout)view.findViewById(R.id.order_cart_layout);
        category_modelList=new ArrayList<>();
        imageSlider=(ImageSlider)view.findViewById(R.id.categoryProduct_image);
        textView_restDetails=(TextView)view.findViewById(R.id.restaurants_deatils_id);
        recyclerView=(RecyclerView)view.findViewById(R.id.sub_menu_recyclerview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        relativeLayout_cart.setVisibility(View.GONE);
        fButton_cartViw=(FButton)view.findViewById(R.id.view_to_Cart);
        textView_countCart=(TextView)view.findViewById(R.id.text_cartCount);
         List<SlideModel> slideModels = new ArrayList<>();

        toolbar=(Toolbar)view.findViewById(R.id.toolbar_Id2);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#ff0090"));

        ProgressDialog progressdialog = new ProgressDialog(getContext());
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();
        progressdialog.setCancelable(false);
        CartOrder_show();

        slideModels.add(new SlideModel(imageUrl,"Restaurants:"+RestaurName+"\nCategory Name:"+categoryName+"\nArea:"+areaName+"\nDelivary Fee:"+delivary_free+"\n Time:"+delivary_time, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(imageUrl,"Restaurants:"+RestaurName+"\nCategory Name:"+categoryName+"\nArea:"+areaName+"\nDelivary Fee:"+delivary_free+"\n Time:"+delivary_time, ScaleTypes.CENTER_CROP));
       imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        StringRequest stringReq=new StringRequest(Request.Method.POST, "https://myfoodpandaproject.000webhostapp.com/category_productShow.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray=new JSONArray(response);
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                           serial_id= jsonObject.getString("Serial_id");
                            sub_menu= jsonObject.getString("sub_menu");
                            productName= jsonObject.getString("product_Name");
                            sellPrice= jsonObject.getInt("selling_price");
                             pd_deatils=  jsonObject.getString("Deatils");
                            image=jsonObject.getString("Image");
                            String pd_image="https://myfoodpandaproject.000webhostapp.com/"+image;

  Category_model  getitem=new Category_model(serial_id,areaName,categoryName,RestaurName,sub_menu,productName,sellPrice,delivary_time,pd_deatils,pd_image);
  category_modelList.add(getitem);
 adpter=new CategoryAdapter(getContext(),category_modelList);
 recyclerView.setAdapter(adpter);
 adpter.notifyDataSetChanged();
  progressdialog.dismiss();


 adpter.setOnItemClick(new CategoryAdapter.onItemClickLisiner() {
     @Override
     public void OnClick_Lisiner(int position) {
        Category_model getItem=category_modelList.get(position);

         BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(getContext());
         layoutInflater=getLayoutInflater();
         view_layout=layoutInflater.inflate(R.layout.order_layout,null);

         textView_view_recipName=(TextView)view_layout.findViewById(R.id.cate_pdName);
         textView_price=(TextView)view_layout.findViewById(R.id.cate_price);
         textView_delivaryTime=(TextView)view_layout.findViewById(R.id.cate_pdTime);
         textView_delivaryFree=(TextView)view_layout.findViewById(R.id.cate_pdDelivary_free);
         imageView_order=(ImageView)view_layout.findViewById(R.id.cate_ordImage);

         fButton_add=(FButton)view_layout.findViewById(R.id.add_to_cartbtn);
         textView_view_recipName.setText("Recipe Name:"+getItem.getProduct_Name());
         textView_delivaryFree.setText("Delivary Free:"+delivary_free);
         textView_delivaryTime.setText("Delivary Time:"+delivary_time);
         textView_price.setText("price:"+getItem.getSell_price());
         Picasso.get().load(getItem.getImage()).into(imageView_order);
         numberButton=(ElegantNumberButton)view_layout.findViewById(R.id.category_Quantity);
         String quanitiy=  numberButton.getNumber().toString();


         fButton_add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             StringRequest stringReq=new StringRequest(Request.Method.POST, "https://myfoodpandaproject.000webhostapp.com/useCart_Add.php", new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response) {
                     Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                     relativeLayout_cart.setVisibility(View.VISIBLE);
                     CartOrder_show();
                     Notification_Add();
                 }
             }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                     Toast.makeText(getActivity(), "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             }){

                 @Override
                 protected Map<String, String> getParams()  {

                     Map<String,String>hasmp_data=new HashMap<String,String>();
                     hasmp_data.put("area_Name",areaName);
                     hasmp_data.put("category_Name",categoryName);
                     hasmp_data.put("rest_Name",RestaurName);
                     hasmp_data.put("Submenu",sub_menu);
                     hasmp_data.put("delivary_free",String.valueOf(delivary_free));
                     hasmp_data.put("delivary_time",delivary_time);

                     hasmp_data.put("prdName",getItem.getProduct_Name());
                     hasmp_data.put("sell_price",String.valueOf(getItem.getSell_price()));
                     hasmp_data.put("discount",String.valueOf(discount));
                     hasmp_data.put("quanitiy",quanitiy);
                     hasmp_data.put("userGamil",UserGamil);
                     hasmp_data.put("Invoice_id",getItem.getSerial_id());
                     return hasmp_data;
                 }
             };
             RequestQueue request_order=Volley.newRequestQueue(getContext());
             request_order.add(stringReq);
             }
         });

         bottomSheetDialog.setContentView(view_layout);
         bottomSheetDialog.show();
     }
 });
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
                Map<String,String>hasmp=new HashMap<String,String>();
                hasmp.put("area_Name",areaName);
                hasmp.put("category_Name",categoryName);
                hasmp.put("rest_Name",RestaurName);
                hasmp.put("sub_menu",sub_menu);
                return hasmp;
            }
        };
        RequestQueue request= Volley.newRequestQueue(getContext());
        request.add(stringReq);

        fButton_cartViw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest string_details=new StringRequest(Request.Method.POST,"https://myfoodpandaproject.000webhostapp.com/user_information_Check.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("1")){
                            Intent intent=new Intent(getContext(),UserAll_Order.class);
                            startActivity(intent);
                        }else{
                            Intent intent=new Intent(getContext(),UserCart_orderShow.class);
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

     public void onBackpressed(){
     AppCompatActivity activity=(AppCompatActivity)getActivity();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_product,new product_showFragment()).addToBackStack(null).commit();
    }


    private void CartOrder_show(){
        StringRequest string_cart=new StringRequest(Request.Method.POST, url_cart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int count=0;
                try {
                 JSONArray jsonArray_cart=  new JSONArray(response);
                 if(!response.contains("Data not found")){
                     for (int i = 0; i <jsonArray_cart.length() ; i++) {
                         // JSONObject jsonObject = jsonArray.getJSONObject(i);
                         count++;
                         textView_countCart.setText("Item:"+count);
                     }
                     relativeLayout_cart.setVisibility(View.VISIBLE);
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

                Map<String,String>hasmp_cart=new HashMap<String,String>();
                hasmp_cart.put("userGamil",UserGamil);
                return  hasmp_cart;
            }
        };
        RequestQueue requestQueue_showCart= Volley.newRequestQueue(getContext());
        requestQueue_showCart.add(string_cart);

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
                .setContentText("Cart SucessFull..")
                .setAutoCancel(true)
                //.setSound(Uri.)
                .setWhen(System.currentTimeMillis());

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getContext());
        notificationManagerCompat.notify(1,notification.build());

    }

}