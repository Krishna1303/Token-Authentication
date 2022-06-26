package com.example.gnit.and04;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gnit.and04.helper.Constants;
import com.example.gnit.and04.helper.Store;
import com.example.gnit.and04.helper.ToastHelper;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class Transfer extends Fragment implements View.OnClickListener {

Spinner spinner;Button btsubmit;
EditText etuserid,etamount;public Bitmap bitmap;
    public final static int QRcodeWidth = 500 ;
    public Transfer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_transfer, container, false);
        HashMap map= Store.getUserDetails(getActivity());
        etuserid=view.findViewById(R.id.etuserid);
         etamount=view.findViewById(R.id.etamount);
        btsubmit=view.findViewById(R.id.btsubmit);
        int userid=Integer.parseInt(map.get("userid").toString());
        etuserid.setText(String.valueOf(userid));
        etuserid.setKeyListener(null);
        spinner=view.findViewById(R.id.spusers);
        getUsers();
        btsubmit.setOnClickListener(this);
        return view;
    }

    private void getUsers() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Constants.viewAppUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList al=new ArrayList();
          Log.e("response",response);
          HashMap map=Store.getUserDetails(getActivity());
          int id=Integer.parseInt(map.get("userid").toString());
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        if(id==jsonObject.getInt("id")){

                        }else {

                            al.add(jsonObject.getInt("id"));
                        }
                    }
                    ArrayAdapter arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,al);
                    spinner.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastHelper.toastMsg(getActivity(),error.toString());
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btsubmit:{
                int userid=Integer.parseInt(etuserid.getText().toString());
                int amount=Integer.parseInt(etamount.getText().toString());
             int receiveid=Integer.parseInt(spinner.getSelectedItem().toString());

              sendToServer(userid,amount,receiveid);

            }
        }
    }

    private void sendToServer(final int userid, final int amount, final int receiveid) {
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.qrcode, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response",response);
                Intent i=new Intent(getActivity(),UserQrCodeScanner.class);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                ToastHelper.toastMsg(getActivity(),error.toString());
                Intent i=new Intent(getActivity(),UserQrCodeScanner.class);
                startActivity(i);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap map=new HashMap();
                map.put("userid",String.valueOf(userid));
                map.put("amount",String.valueOf(amount));
                map.put("receiverid",String.valueOf(receiveid));
                return map;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    private void transferAmount(final int userid, final int amount, final int receiveid) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.sendMoney, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
Log.e("response",response);
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
                map.put("amount",String.valueOf(amount));
                map.put("receiverid",String.valueOf(receiveid));
                return map;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private Bitmap TextToImageEncode(String Value) {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (Exception e) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
