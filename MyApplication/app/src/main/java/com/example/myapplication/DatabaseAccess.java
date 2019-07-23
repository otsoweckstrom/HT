package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    //private constructor
    public DatabaseAccess(Context context){
        this.openHelper = new DatabaseHelper(context);

    }
    public static DatabaseAccess getInstance(Context context){
        if(instance==null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    //opening the database
    public void openDatabase(){
        this.db = openHelper.getWritableDatabase();
    }

    //closing the database connection
    public void closeDatabase(){
        if(db != null){
            this.db.close();
        }
    }

    //SQL query that creates the user object
    public User createUser(String name) {
        String query = "select * from userAccount where loginName = '" + name+"'";
        openDatabase();
        Cursor cursor = db.rawQuery(query, null);

        User user = new User();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            user.setUserID(cursor.getInt(0));
            user.setLoginName(cursor.getString(1));
            user.setLoginPassword(cursor.getString(2));
            cursor.close();
        } else {
            user = null;
        }
        db.close();
        return user;
    }
    public String getLoginPassword(String name) {
        String query = "select(loginPassword) from userAccount where loginName = '" + name+"'";
        String password = "";
        openDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            password = cursor.getString(0);
            cursor.close();
        }
        db.close();
        return password;
    }
    //Method for changing users name
    public void changeUserName(String name, int userID){
        String query ="UPDATE userAccount SET loginName = '" + name +"' WHERE userID = " + userID;
        openDatabase();
        db.execSQL(query);
        db.close();
    }
    //Method for changing users password
    public void changePassword(String password, int userID){
        String query ="UPDATE userAccount SET loginPassword = '" + password +"' WHERE userID = " + userID;
        openDatabase();
        db.execSQL(query);
        db.close();
    }

    //Methods for adding money and making payments
    public void addMoneyToAccount(String type, String accountNumber, float amount){
        String query ="UPDATE " + type + "Account SET balance = balance + " + amount + " WHERE accountNumber ='" + accountNumber +"'";
        System.out.println(query);
        openDatabase();
        db.execSQL(query);
        db.close();
    }
    public void makePayment(String type, String accountNumber, float amount){
        String query = "UPDATE " + type + "Account SET balance = balance - " + amount + " WHERE accountNumber ='" +accountNumber +"'";
        openDatabase();
        db.execSQL(query);
        db.close();
    }

    //These methods return an arraylist that contains an object of every Account for that type, the object stores all the information the account has
    public ArrayList<DebitAccountClass> getDebitAccounts(int userID) {
        ArrayList<DebitAccountClass> account = new ArrayList<DebitAccountClass>();
        String query = "select * from debitAccount where userID = ?";
        openDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{Integer.toString(userID)});
        if (cursor.moveToFirst()) {
            do {
                account.add(new DebitAccountClass(cursor.getString(0), cursor.getInt(1), cursor.getFloat(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return account;
    }
    public float getDebitAccountPayCeiling(String accountNumber){
        String query = "SELECT balance from debitAccount WHERE accountNumber = '" + accountNumber + "'";
        openDatabase();
        System.out.println(query);
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            float balance = cursor.getFloat(0);
            db.close();
            return balance;
        }
        db.close();
        return 0;
    }
    public float getCreditAccountPayCeiling(String accountNumber){
        String query = "SELECT balance, creditAmount from creditAccount WHERE accountNumber = '" + accountNumber + "'";
        openDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            float balance = cursor.getFloat(0) + cursor.getFloat(1);
            db.close();
            return balance;
        }
        db.close();
        return 0;
    }


    public ArrayList<CreditAccountClass> getCreditAccounts(int userID) {
        ArrayList<CreditAccountClass> account = new ArrayList<CreditAccountClass>();
        String query = "select * from creditAccount where userID = '" + userID + "'";
        openDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do{
                account.add(new CreditAccountClass(cursor.getString(0),cursor.getInt(1),cursor.getFloat(2), cursor.getFloat(3)));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return account;
    }
    public ArrayList<SavingsAccountClass> getSavingsAccounts(int userID) {
        ArrayList<SavingsAccountClass> account = new ArrayList<SavingsAccountClass>();
        String query = "select * from savingsAccount where userID = " + userID;

        openDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do{
                account.add(new SavingsAccountClass(cursor.getString(0),cursor.getInt(1),cursor.getFloat(2), cursor.getFloat(3)));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return account;
    }


    //Method for getting the users bank cards
    public ArrayList<BankCard> getDebitCards(int userID){
        ArrayList<BankCard> bankCards = new ArrayList<>();
        String query = "select cardID, cardNumber, debitCard.accountNumber from userAccount join debitAccount on debitAccount.userID = userAccount.userID join debitCard on debitCard.accountNumber = debitAccount.accountNumber where userAccount.userID = " + userID;
        openDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                bankCards.add(new BankCard(cursor.getInt(0),cursor.getString(1),cursor.getString(2),"debit"));
            }while(cursor.moveToNext());
        }
        closeDatabase();
        cursor.close();

        return bankCards;
    }

    public ArrayList<BankCard> getCreditCards(int userID){
        ArrayList<BankCard> bankCards = new ArrayList<>();
        String query = "select cardID, cardNumber, creditCard.accountNumber from userAccount join creditAccount on creditAccount.userID = userAccount.userID join creditCard on creditCard.accountNumber = creditAccount.accountNumber where userAccount.userID = " + userID;
        openDatabase();
        Cursor cursor2 = db.rawQuery(query, null);
        if (cursor2.moveToFirst()){
            do{
                bankCards.add(new BankCard(cursor2.getInt(0),cursor2.getString(1),cursor2.getString(2),"credit"));
            }while(cursor2.moveToNext());
        }
        closeDatabase();
        cursor2.close();
        return bankCards;
    }


    //Method for registering a new user into the database
    public boolean newUserRegister(String name, String password){
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("loginName", name);
        contentValues.put("loginPassword",password);
        long result = db.insert("userAccount",null,contentValues);

        closeDatabase();
        if(result == -1){
            return false;
        }else return true;
    }

    //Methods for creating a new account for the user
    public boolean createNewDebitAccount(String accountNumber, int userID, float balance){
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountNumber",accountNumber);
        contentValues.put("userID",userID);
        contentValues.put("balance",balance);
        long result = db.insert("debitAccount",null,contentValues);
        closeDatabase();
        if(result == -1){
            return false;
        }else return true;
    }
    public boolean createNewCreditAccount(String accountNumber, int userID, float balance, float creditAmount){
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountNumber",accountNumber);
        contentValues.put("userID",userID);
        contentValues.put("balance",balance);
        contentValues.put("creditAmount",creditAmount);
        long result = db.insert("creditAccount",null,contentValues);
        closeDatabase();
        if(result == -1){
            return false;
        }else return true;

    }
    public boolean createNewSavingsAccount(String accountNumber, int userID, float balance, float savingsPer){
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("accountNumber",accountNumber);
        contentValues.put("userID",userID);
        contentValues.put("balance",balance);
        contentValues.put("savingsPercentage",savingsPer);
        long result = db.insert("savingsAccount",null,contentValues);
        closeDatabase();
        if(result == -1){
            return false;
        }else return true;

    }

    //Methods for creating new cards
    public boolean createNewDebitCard( String accountNumber, String cardNumber){
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cardNumber",cardNumber);
        contentValues.put("accountNumber",accountNumber);
        long result = db.insert("debitCard",null,contentValues);
        closeDatabase();
        if(result == -1){
            return false;
        }else return true;
    }
    public boolean createNewCreditCard( String accountNumber, String cardNumber){
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cardNumber",cardNumber);
        contentValues.put("accountNumber",accountNumber);
        long result = db.insert("creditCard",null,contentValues);
        closeDatabase();
        if(result == -1){
            return false;
        }else return true;
    }

    //Methods for deleting database data
    public void deleteBankAccount(String accountName, int choice)
    {
        if(choice == 1){
            openDatabase();
            db.execSQL("PRAGMA foreign_keys=ON");
            db.delete("debitAccount", "accountNumber" + "=?", new String[]{accountName});
            db.close();
        }else if(choice == 2){
            openDatabase();
            db.execSQL("PRAGMA foreign_keys=ON");
            db.delete("creditAccount", "accountNumber" + "=?", new String[]{accountName});
            db.close();
        }else if (choice == 3){
            openDatabase();
            db.execSQL("PRAGMA foreign_keys=ON");
            db.delete("savingsAccount", "accountNumber" + "=?", new String[]{accountName});
            db.close();

        }
    }
    public void deleteCard(String name, int choice){
        if(choice == 1){
            openDatabase();
            db.execSQL("PRAGMA foreign_keys=ON");
            db.delete("debitCard", "cardNumber" + "=?", new String[]{name});
            db.close();
        }else if(choice == 2){
            openDatabase();
            db.execSQL("PRAGMA foreign_keys=ON");
            db.delete("creditCard", "cardNumber" + "=?", new String[]{name});
            db.close();
        }
    }
    public void deleteUserAccount(int userID){
            openDatabase();
            db.execSQL("PRAGMA foreign_keys=ON");
            db.delete("userAccount", "userID" + "=?", new String[]{""+userID});
            db.close();
    }

}

