package com.example.gnit.and04;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gnit.and04.helper.Constants;
import com.example.gnit.and04.helper.ToastHelper;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shyam on 07-01-2018.
 */

public class Register extends Activity {

    EditText etusername,etname,etpassword,etemail,etmobile,etuserid,etrole,etownerusername;
    String usernam,name,password,email,mobile;
    Button button,btuserid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etusername=findViewById(R.id.etusername);
      etuserid=findViewById(R.id.etuserid);
        etpassword=findViewById(R.id.etpassword);
        etemail=findViewById(R.id.etemail);
        etmobile=findViewById(R.id.etmobile);
        etemail=findViewById(R.id.etemail);
        etrole=findViewById(R.id.etrole);
        etownerusername=findViewById(R.id.etownerusername);
        button=findViewById(R.id.btsignup);
        btuserid=findViewById(R.id.btuserid);
        etusername.setKeyListener(null);
        etmobile.setKeyListener(null);
        etemail.setKeyListener(null);
        etrole.setKeyListener(null);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setBackgroundColor(getResources().getColor(R.color.black));
                String userid=etuserid.getText().toString();
               String username=etusername.getText().toString();
               String email=etemail.getText().toString();
               String mobile=etmobile.getText().toString();
               String password=etpassword.getText().toString();
               String username1=etownerusername.getText().toString();
               if(userid.isEmpty() || userid==null){
                   ToastHelper.toastMsg(getApplicationContext(),"check user details");
               }else if(username1.isEmpty() || username1==null){
                   ToastHelper.toastMsg(getApplicationContext(),"Username cannot be empty");
               }else if(username.isEmpty() || username==null){
                   ToastHelper.toastMsg(getApplicationContext(),"check user details");
               }else if(email.isEmpty()|| email==null){
                   ToastHelper.toastMsg(getApplicationContext(),"check user details");
               }else if(mobile.isEmpty() || mobile==null){
                   ToastHelper.toastMsg(getApplicationContext(),"check user details");
               }else if(password.isEmpty() || password==null){
                   etpassword.setError("password required");
               }else{
                   registerUser(Integer.parseInt(userid),username1,password);
               }
            }
        });

        btuserid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid=etuserid.getText().toString();
                if(userid.isEmpty() || userid==null){
                    etuserid.setError("account id is required");
                }else{
                    getUserDetails(Integer.parseInt(userid));

                }

            }
        });

    }

    private void getUserDetails(final int i) {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Getting....");
        pd.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.getUserDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response",response);
                if(response.isEmpty() || response==null){
                    pd.dismiss();
                    ToastHelper.toastMsg(getApplicationContext(),"No account found");
                }else {
                    pd.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        etusername.setText(jsonObject.getString("name"));
                        etemail.setText(jsonObject.getString("email"));
                        etmobile.setText(jsonObject.getString("mobile"));
                        etrole.setText(jsonObject.getString("address"));
                    } catch (JSONException e) {
                        pd.dismiss();
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastHelper.toastMsg(getApplicationContext(),error.toString());
                pd.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap();
                map.put("accid", String.valueOf(i));


                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void registerUser(final int userid, final String s, final String password) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.regUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
Log.e("response",response);
Intent intent=new Intent(getApplicationContext(),MainActivity.class);
finish();
startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
ToastHelper.toastMsg(getApplicationContext(),error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap();
                map.put("accid",String.valueOf(userid));
                map.put("username",s);
                map.put("password",password);

                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
