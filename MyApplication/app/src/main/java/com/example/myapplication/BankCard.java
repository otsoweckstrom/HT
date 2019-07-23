package com.example.myapplication;

import java.io.Serializable;

public class BankCard implements Serializable {

    int cardID;
    String cardNumber;
    String accountNumber;
    String type;

    BankCard(int cardID, String cardNumber, String accountNumber, String type){
        this.cardID = cardID;
        this.cardNumber = cardNumber;
        this.accountNumber = accountNumber;
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
