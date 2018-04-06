package work.test.sergii.pwwallet.entities;

import android.content.ContentValues;

import work.test.sergii.pwwallet.db.DBContract;

/**
 * Created by sergii on 26.03.18.
 */

public class Transaction {

    private int transactionId;
    private long transationTime;
    private User correspondent;
    private double amount;
    private double finalBalance;


    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public long getTransationTime() {
        return transationTime;
    }

    public void setTransationTime(long transationTime) {
        this.transationTime = transationTime;
    }

    public User getCorrespondentName() {
        return correspondent;
    }

    public void setCorrespondentName(User correspondentName) {
        this.correspondent = correspondentName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(double finalBalance) {
        this.finalBalance = finalBalance;
    }

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();

        cv.put(DBContract.Transaction.AMOUNT, this.amount);
        cv.put(DBContract.Transaction.CORRESPONDENT_ID, this.correspondent.getId());
        cv.put(DBContract.Transaction.TRANSACTION_TIME, this.transationTime);
        cv.put(DBContract.Transaction.BALANCE, this.finalBalance);

        return cv;
    }

}
