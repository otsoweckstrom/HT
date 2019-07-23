package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bank.db";
    private static final String DATABASE_NAME_SQL = "database.sql";
    private static int DATABASE_VERSION = 24;
    private Context mContext;
    private SQLiteDatabase mDatabase;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    //Creates the database from the .sql file when called
    @Override
    public void onCreate(SQLiteDatabase db) {
        InputStream inputStream = null;
        try {
            inputStream = mContext.getAssets().open(DATABASE_NAME_SQL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                if (line.contains(";")) {
                    db.execSQL(stringBuilder.toString());
                    stringBuilder.setLength(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    //if the .sql file is updated this method is called (remember to +1 the version number)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS debitCard");
            db.execSQL("DROP TABLE IF EXISTS creditCard");
            db.execSQL("DROP TABLE IF EXISTS debitAccount");
            db.execSQL("DROP TABLE IF EXISTS savingsAccount");
            db.execSQL("DROP TABLE IF EXISTS creditAccount");
            db.execSQL("DROP TABLE IF EXISTS userAccount");
            db.execSQL("DROP TABLE IF EXISTS bank");


            onCreate(db);
        }
    }
}