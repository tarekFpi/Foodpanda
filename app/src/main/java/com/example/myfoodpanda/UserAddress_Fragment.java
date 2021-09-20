package com.example.myfoodpanda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UserAddress_Fragment extends Fragment {
private EditText editText_userName,editText_gmail,editText_area,editText_float_number,editText_city,editText_phone;
private Button button_add;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
  private String UserGamil="";
  private Toolbar toolbar;
  private SharedPreferences sharedPreferences_gamil;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserAddress_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserAddress_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAddress_Fragment newInstance(String param1, String param2) {
        UserAddress_Fragment fragment = new UserAddress_Fragment();
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
        View view= inflater.inflate(R.layout.fragment_user_address_, container, false);

        editText_area=(EditText)view.findViewById(R.id.user_location);
        editText_phone=(EditText)view.findViewById(R.id.user_phon);
        editText_float_number=(EditText)view.findViewById(R.id.user_flat_no_id);
        editText_gmail=(EditText)view.findViewById(R.id.user_email);
        editText_userName=(EditText)view.findViewById(R.id.user_name);
        editText_city=(EditText)view.findViewById(R.id.user_city_id);
        button_add=(Button)view.findViewById(R.id.userAddress_Add);
      toolbar=(Toolbar)view.findViewById(R.id.toolbar_Id);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        sharedPreferences_gamil=getContext().getSharedPreferences("com.example.myfoodpanda",Context.MODE_PRIVATE);
        UserGamil= sharedPreferences_gamil.getString("userGmail","user Gamil NOt Found");
        editText_gmail.setText(""+UserGamil);
        editText_gmail.setEnabled(false);

      button_add.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              String areaName=editText_area.getText().toString();
              String userName=editText_userName.getText().toString();
              String Gmail=editText_gmail.getText().toString();
              String phoneNumber=editText_phone.getText().toString();
              String floatNumber=editText_float_number.getText().toString();
              String city=editText_city.getText().toString();

              StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://myfoodpandaproject.000webhostapp.com/Delivary_information.php", new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                     if(response.toString().contains("Your Information Add SuccessFull..")){
                         Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                         editText_area.setText("");
                         editText_userName.setText("");
                         editText_gmail.setText("");
                         editText_phone.setText("");
                         editText_float_number.setText("");
                         editText_city.setText("");
                         Intent intent=new Intent(getContext(),UserAll_Order.class);
                         startActivity(intent);
                     }else{
                         Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
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

                    Map<String,String>hasmp_data=new HashMap<String,String>();
                    hasmp_data.put("user_name",userName);
                    hasmp_data.put("Gmail",UserGamil);
                    hasmp_data.put("areaName",areaName);
                   hasmp_data.put("flat_on",floatNumber);
                   hasmp_data.put("city",city);
                   hasmp_data.put("phone",phoneNumber);
                  return hasmp_data;

                  }
              };

              RequestQueue requestQueue= Volley.newRequestQueue(getContext());
              requestQueue.add(stringRequest);
          }
      });


        return  view;
    }
}