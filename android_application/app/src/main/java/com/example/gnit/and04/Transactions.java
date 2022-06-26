package com.example.gnit.and04;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Transactions extends Fragment {

ListView lvlist;
Context context;
    public Transactions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_transactions, container, false);
        lvlist=view.findViewById(R.id.lvlist);
        context=getActivity();
        HashMap map= Store.getUserDetails(getActivity());
        getTransactions(Integer.parseInt(map.get("userid").toString()));

        return  view;
    }

    private void getTransactions(int userid) {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Constants.userTransactions+"?userid="+userid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("password",response);
                List<TransactionModel> transactionModelList=new ArrayList<>();
                if(!(response==null || response.isEmpty())){
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            TransactionModel tm=new TransactionModel();
                         tm.setReciverid(jsonObject.getInt("reciverid"));
                         tm.setAmount(jsonObject.getDouble("amount"));
                         tm.setDate(jsonObject.getString("date"));
                       transactionModelList.add(tm);

                        }
                        displayToarray(context,transactionModelList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    ToastHelper.toastMsg(getActivity(),"Old password is wrong");
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

    private void displayToarray(Context context, List<TransactionModel> transactionModelList) {

    TransactionAdapter transactionAdapter=new TransactionAdapter(context,transactionModelList);
    lvlist.setAdapter(transactionAdapter);
    }


}
