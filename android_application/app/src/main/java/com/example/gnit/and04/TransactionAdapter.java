package com.example.gnit.and04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shyam on 29-01-2018.
 */

class TransactionAdapter extends BaseAdapter {
    Context context;
    List<TransactionModel> transactionModelList;
    public TransactionAdapter(Context context, List<TransactionModel> transactionModelList) {
        this.context=context;
        this.transactionModelList=transactionModelList;
    }

    @Override
    public int getCount() {
        return transactionModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TransactionModel tm=transactionModelList.get(i);
      view= LayoutInflater.from(context).inflate(R.layout.row,null);
      TextView tvrid=view.findViewById(R.id.tvrid);
      TextView tvdate=view.findViewById(R.id.tvdate);
      TextView tvamount=view.findViewById(R.id.tvamount);
      tvrid.setText(String.valueOf(tm.getReciverid()));
      tvdate.setText(tm.getDate());
      tvamount.setText(String.valueOf(tm.getAmount()));
      return  view;
    }
}
