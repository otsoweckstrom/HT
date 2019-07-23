package com.example.myapplication;

import java.io.Serializable;

public class SavingsAccountClass implements Serializable {

    int userID;
    float balance;
    String accountNumber;
    float savingsPercentage;
    final String type = "savings";

    SavingsAccountClass(String accountNumber, int userID, float balance, float savingsPercentage) {
        this.userID = userID;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.savingsPercentage = savingsPercentage;

    }

    public String getType(){
        return type;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }


    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }
}


