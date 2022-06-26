package com.example.gnit.and04;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.gnit.and04.helper.Constants;
import com.example.gnit.and04.helper.Store;
import com.example.gnit.and04.helper.ToastHelper;


import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePassword extends Fragment implements View.OnClickListener {

    EditText etoldpass,etnewpass,etrepass;
    Button btsubmit;String newpassword;int userid;


    public ChangePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_change_password, container, false);
        etoldpass=view.findViewById(R.id.etoldpass);
        etnewpass=view.findViewById(R.id.etnewpass);
        etrepass=view.findViewById(R.id.etrepass);
        btsubmit=view.findViewById(R.id.btsubmit);
        btsubmit.setOnClickListener(this);

    return view;
    }

    @Override
    public void onClick(View view) {
        String oldpassword=etoldpass.getText().toString();
        String newpass=etnewpass.getText().toString();
        String newrepass=etrepass.getText().toString();
        if(oldpassword.isEmpty() || oldpassword==null){
            etoldpass.setError("old password required");
        }else if(newpass.isEmpty() || newpass==null){
            etnewpass.setError("password required");

        }else if(newrepass.isEmpty() || newrepass==null){
            etrepass.setError("password required");
        }else if(!(newrepass.equals(newpass))){
            etrepass.setError("password Not matched");
        }

        else{
            HashMap map= Store.getUserDetails(getActivity());
            newpassword=etnewpass.getText().toString();
              userid=Integer.parseInt(map.get("userid").toString());
           getOldPass(oldpassword,map.get("username").toString());
          /*  if(val){
                HashMap map= Store.getUserDetails(getActivity());
              long i= updatePassword(newpass,Integer.parseInt(map.get("userid").toString()));
              if(i!=-1){
                  ToastHelper.toastMsg(getActivity(),"password updated");
              }else{
                  ToastHelper.toastMsg(getActivity(),"password update failed");
              }
            }else{
                ToastHelper.toastMsg(getActivity(),"password is wrong");
            }*/
        }
    }



    private void getOldPass(final String oldpassword, final String username) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.oldPassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("password",response);
                if(response.equalsIgnoreCase("success")){
                 changePassword(userid,newpassword);
                }else{
                    ToastHelper.toastMsg(getActivity(),"Old password is wrong");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
ToastHelper.toastMsg(getActivity(),error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap map=new HashMap();
                map.put("oldpassword",oldpassword);
                map.put("username",username);
                return map;
            }
        };

        RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void changePassword(final int userid, final String newpassword) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.changepassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("password",response);
                if(response.equalsIgnoreCase("success")){
                   ToastHelper.toastMsg(getActivity(),"password changed successfully");
                }else{
                    ToastHelper.toastMsg(getActivity(),"password update failed");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastHelper.toastMsg(getActivity(),error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap map=new HashMap();
                map.put("userid",String.valueOf(userid));
                map.put("password",newpassword);
                return map;
            }
        };

        RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


}
