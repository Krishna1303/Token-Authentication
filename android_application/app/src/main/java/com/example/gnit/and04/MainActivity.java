package com.example.gnit.and04;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvlogo;
    EditText etusername,etpassword;
    Button btsubmit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button btreg;

    private static final int REQUEST_CODE_ASK_PERMISSIONS =1 ;
    private static final int PERMS_REQUEST_CODE =123 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(hasPermissions()){
            //  crime_status();
        }else{
            requestPerms();
        }

        Typeface awesomefont=Typeface.createFromAsset(getAssets(),"fonts.ttf");
        etusername=findViewById(R.id.etusername);
        etpassword=findViewById(R.id.etpasswrod);
        tvlogo=findViewById(R.id.tvlogo);
        btsubmit=findViewById(R.id.btsubmit);
        btreg=findViewById(R.id.btreg);
        tvlogo.setTypeface(awesomefont);
        btsubmit.setOnClickListener(this);
        btreg.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btsubmit:{
                String username = etusername.getText().toString();
                String password = etpassword.getText().toString();
                if (username.isEmpty() || username == null) {
                    etusername.setError("Enter Username");
                } else if (password.isEmpty() || password == null) {
                    etpassword.setError("Password required");
                } else {
                    if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
                       // Intent i = new Intent(this, AdminSuccessLogin.class);
                        //startActivity(i);
                    } else {
                        CheckLogin(username,password);
                      //  Intent i = new Intent(this, User_success.class);
                      //  startActivity(i);
                    }
                }
                break;
            }
            case R.id.btreg:{
                Intent i=new Intent(this,Register.class);
               startActivity(i);
                break;
            }
        }

    }



    private void CheckLogin(final String username, final String password) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.userLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int id=jsonObject.getInt("id");

                    String name=jsonObject.getString("username");
                    String role=jsonObject.getString("amount");


                    Store.userDetails(getApplicationContext(),id,name,role);
                    if(role.equalsIgnoreCase("Team Leader")){
                      //  Intent i = new Intent(getApplicationContext(), User_success.class);
                        finish();
                       // startActivity(i);

                    }else {
                        Intent i = new Intent(getApplicationContext(), user_success.class);
                        finish();
                        startActivity(i);

                    }
                } catch (JSONException e) {
                    ToastHelper.toastMsg(getApplicationContext(),"error occured");
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("username",username);
                map.put("password",password);
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed =true;
        switch(requestCode){
            case PERMS_REQUEST_CODE:
                for (int res : grantResults) {
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;

            default:
                allowed=false;
                break;

        }
        if(allowed){

        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){

                }
            }
        }

    }

    private void requestPerms(){
        String [] permissions=new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMS_REQUEST_CODE);
        }

    }

    @SuppressLint("WrongConstant")
    private boolean hasPermissions(){
        int res=0;
        String[] permissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for(String perms:permissions){
            res=this.checkCallingOrSelfPermission(perms);
            if(!(res== PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }
}
