package com.example.gnit.and04;

/**
 * Created by shyam on 29-01-2018.
 */

public class TransactionModel {

    int id;
    int senderid;
    int reciverid;
    double amount;
    String date;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSenderid() {
        return senderid;
    }
    public void setSenderid(int senderid) {
        this.senderid = senderid;
    }
    public int getReciverid() {
        return reciverid;
    }
    public void setReciverid(int reciverid) {
        this.reciverid = reciverid;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
