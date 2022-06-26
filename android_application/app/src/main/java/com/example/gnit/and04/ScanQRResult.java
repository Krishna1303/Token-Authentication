package com.example.gnit.and04;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
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
import com.example.gnit.and04.helper.Constants;
import com.example.gnit.and04.helper.Store;
import com.example.gnit.and04.helper.ToastHelper;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by shyam on 11-01-2018.
 */

public class ScanQRResult  extends Activity implements View.OnClickListener {
    EditText etmname,etamount;
    int mid;Button btpay;
    String userid,rid,amount,token;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        etmname=findViewById(R.id.mname);
        etamount=findViewById(R.id.amount);
        btpay=findViewById(R.id.btpay);
        Bundle bundle=getIntent().getExtras();
     userid=bundle.getString("userid");
     rid=bundle.getString("rid");
     amount=bundle.getString("amount");
     token=bundle.getString("token");
        etmname.setText(bundle.getString("token"));
        etamount.setText(amount);
        etmname.setKeyListener(null);
        etamount.setKeyListener(null);
        btpay.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
      Double amount1=Double.parseDouble(amount);
      transferamount(userid,amount,rid);

    }

    private void transferamount(final String userid, final String amount, final String rid) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.sendMoney, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response",response);
                if(response.equalsIgnoreCase("success")){
                    ToastHelper.toastMsg(getApplicationContext(),"transaction Success");
                    Intent i=new Intent(getApplicationContext(),user_success.class);
                    startActivity(i);
                }else{
                    ToastHelper.toastMsg(getApplicationContext(),"transaction failed");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastHelper.toastMsg(getApplicationContext(),error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap map=new HashMap();
                map.put("userid",String.valueOf(userid));
                map.put("amount",String.valueOf(amount));
                map.put("receiverid",String.valueOf(rid));
                return map;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

   /* private void payment(final int mid, final int userid,final double amount) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
Log.e("response",response);
ToastHelper.toastMsg(getApplicationContext(),"payment success");
Intent i=new Intent(getApplicationContext(),BankDetailsSuccess.class);
finish();
startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastHelper.toastMsg(getApplicationContext(),error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap map=new HashMap();
                map.put("mid",String.valueOf(mid));
                map.put("userid",String.valueOf(userid));
                map.put("amount",String.valueOf(amount));
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/
}
