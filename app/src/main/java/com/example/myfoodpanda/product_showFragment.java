package com.example.myfoodpanda;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfoodpanda.Model.ProductShow_Model;
import com.example.myfoodpanda.Model.Product_show_Adpter;
import com.example.myfoodpanda.Model.getRestaurantModel;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class product_showFragment extends Fragment {
  private RecyclerView recyclerView;
  private Product_show_Adpter adpter;
  private List<ProductShow_Model>modelList;
  private String url="https://myfoodpandaproject.000webhostapp.com/productShow.php";
  private int delivary_free;
  private   String serial_id,areaName,categoryName,restaurName,sub_menu,delivary_time, Rest_deatils,closeing_time;
   private SharedPreferences sharedPreferences_gamil;
    String rest_Name="";
    String rest_Area="";
    String res_Cate="";
    String image="";
    private TextView textView_userName,textView_userGmail;
    private ImageView imageView_user;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggleButton;
    private Toolbar toolbar;
    String personName ;
    String personGivenName;
    String personEmail;
    Uri personPhoto ;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String UserGamil="";
    private String mParam1;
    private String mParam2;
     private GoogleSignInClient mGoogleSignInClient;
    public product_showFragment() {
     // Required empty public constructor
    }

    public static product_showFragment newInstance(String param1, String param2) {
        product_showFragment fragment = new product_showFragment();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_show, container, false);

        modelList=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recycler_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        drawerLayout=(DrawerLayout)view.findViewById(R.id.drawerlayout_user);
        navigationView=(NavigationView)view.findViewById(R.id.navigation_id);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar_Id);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

      GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            personName = acct.getDisplayName();
            personGivenName = acct.getGivenName();
            personEmail = acct.getEmail();
           /*  String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();*/
           personPhoto = acct.getPhotoUrl();
            sharedPreferences_gamil=getContext().getSharedPreferences("com.example.myfoodpanda", Context.MODE_PRIVATE);
            sharedPreferences_gamil.edit().putString("userGmail",acct.getEmail()).apply();
            sharedPreferences_gamil.edit().commit();

        }


        toggleButton=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.open,R.string.close);
        toggleButton.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(Color.parseColor("#ff0090"));
        drawerLayout.addDrawerListener(toggleButton);
        toggleButton.syncState();

       View navigation_view=navigationView.inflateHeaderView(R.layout.header);
        textView_userName=(TextView)navigation_view.findViewById(R.id.userName);
         textView_userGmail=(TextView)navigation_view.findViewById(R.id.user_gmail);
        imageView_user=(ImageView)navigation_view.findViewById(R.id.user_image);
        textView_userGmail.setText("Gmail:"+personEmail);
        textView_userName.setText("Name:"+personName);
        Picasso.get().load(personPhoto).into(imageView_user);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){

                    case R.id.home_id:
                        Toast.makeText(getContext(), "Home:", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case  R.id.setting_Id:
                        Toast.makeText(getContext(), "Setting:", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case  R.id.user_cartId:
                        UserGamil= sharedPreferences_gamil.getString("userGmail","Gmail Not Found");

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
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.exit_id:
                        Toast.makeText(getContext(), "Exit", Toast.LENGTH_SHORT).show();
                        mGoogleSignInClient.signOut();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });

        ProgressDialog progressdialog = new ProgressDialog(getContext());
        progressdialog.setMessage("Please Wait...");
         progressdialog.show();
        progressdialog.setCancelable(false);

        DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        String currentTime = dateFormat.format(new Date()).toString();

     //   toggleButton=new ActionBarDrawerToggle(getContext(),);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                     modelList.clear();
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i = 0; i <jsonArray.length() ;i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        serial_id= jsonObject.getString("Serial_id");
                        areaName= jsonObject.getString("AreaName");
                        categoryName= jsonObject.getString("Category_Name");
                        restaurName= jsonObject.getString("Restaurant_NameAdd");
                        sub_menu= jsonObject.getString("sub_menu");
                        closeing_time= jsonObject.getString("closeing_time");
                        delivary_free= jsonObject.getInt("Delivery_free");
                        delivary_time= jsonObject.getString("Delivary_time");
                        Rest_deatils=  jsonObject.getString("Deatils");

     String rest_Image="https://myfoodpandaproject.000webhostapp.com/"+jsonObject.getString("image_rest");

    ProductShow_Model item=new  ProductShow_Model(serial_id,areaName,categoryName,restaurName,sub_menu,delivary_time,Rest_deatils,delivary_free,closeing_time,rest_Image);
       modelList.add(item);

                        adpter=new Product_show_Adpter(getContext(),modelList);
                        recyclerView.setAdapter(adpter);
                        progressdialog.dismiss();
                        adpter.setOnItemClick(new Product_show_Adpter.onItemClickLisiner() {
                            @Override
                            public void OnClick_Lisiner(int position) {

                 ProductShow_Model getItem=modelList.get(position);
                  AppCompatActivity activity=(AppCompatActivity)getActivity();
    activity.getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_product,new Food_category(getItem.getAreaName(),getItem.getCategoryName(),getItem.getRest_Image(),getItem.getRestaurants_name(),getItem.getSub_menu(),getItem.getDelivary_free(),getItem.getRest_Deatils(),getItem.getDelivary_time())).addToBackStack(null).commit();
          }
      } );
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
        });
        RequestQueue requestQueue=Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        return  view;
    }


}