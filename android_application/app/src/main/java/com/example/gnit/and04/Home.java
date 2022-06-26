package com.example.gnit.and04;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gnit.and04.helper.Store;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_home, container, false);
        Typeface awesomefont=Typeface.createFromAsset(getActivity().getAssets(),"fonts.ttf");
        TextView textView=view.findViewById(R.id.tvuser);
        TextView tvusername=view.findViewById(R.id.tvname);
        TextView tvbalance=view.findViewById(R.id.tvbalance);
        HashMap map= Store.getUserDetails(getActivity());
        tvusername.setText(map.get("username").toString());
        tvbalance.setText(map.get("type").toString());
        textView.setTypeface(awesomefont);
        return view;
    }

}
