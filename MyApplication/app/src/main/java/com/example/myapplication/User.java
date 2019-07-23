package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    int userID;
    String loginName;
    String loginPassword;
    ArrayList<BankCard> debitCards;
    ArrayList<BankCard> creditCards;
    ArrayList<SavingsAccountClass> savingsAccounts;
    ArrayList<CreditAccountClass> creditAccounts;
    ArrayList<DebitAccountClass> debitAccounts;
    ArrayList<String> allAccounts = new ArrayList<>();
    ArrayList<String> allCards = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    public User() {

    }

    public void setCreditAccounts(ArrayList<CreditAccountClass> creditAccounts) {
        this.creditAccounts = creditAccounts;
    }

    public ArrayList<CreditAccountClass> getCreditAccounts() {
        return creditAccounts;
    }

    public void setDebitAccounts(ArrayList<DebitAccountClass> debitAccounts) {
        this.debitAccounts = debitAccounts;
    }

    public void setSavingsAccounts(ArrayList<SavingsAccountClass> savingsAccounts) {
        this.savingsAccounts = savingsAccounts;
    }

    public void setAllAccounts(ArrayList<String> allAccounts) {
        this.allAccounts = allAccounts;
    }

    public void setAllCards(ArrayList<String> allCards) {
        this.allCards = allCards;
    }

    public void setCreditCards(ArrayList<BankCard> creditCards) {
        this.creditCards = creditCards;
    }

    public void setDebitCards(ArrayList<BankCard> debitCards) {
        this.debitCards = debitCards;
    }

    public ArrayList<BankCard> getCreditCards() {
        return creditCards;
    }

    public ArrayList<BankCard> getDebitCards() {
        return debitCards;
    }

    public ArrayList<DebitAccountClass> getDebitAccounts() {
        return debitAccounts;
    }

    public ArrayList<SavingsAccountClass> getSavingsAccounts() {
        return savingsAccounts;
    }

    public ArrayList<String> getAllAccounts() {
        return allAccounts;
    }

    public ArrayList<String> getAllCards() {
        return allCards;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public void setLoginPassword(String p) {
        this.loginPassword = p;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginName(String n) {
        this.loginName = n;
    }

    public String getLoginName() {
        return loginName;
    }


}
