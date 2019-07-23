package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddMoneyActivity extends AppCompatActivity {
    User user;
    int userID;
    DatabaseAccess databaseAccess;
    Button btn_addMoney;
    Spinner spinner_allBankAccounts;
    EditText et_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        userID = user.getUserID();
        databaseAccess = new DatabaseAccess(getApplicationContext());

        btn_addMoney = findViewById(R.id.btn_addMoney);
        spinner_allBankAccounts = findViewById(R.id.spinner_allAccounts);
        et_amount = findViewById(R.id.et_amount);

        addItemsOnSpinnerAllAccounts();

        btn_addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_amount.getText().toString().isEmpty()){
                    float amount = Float.parseFloat(et_amount.getText().toString());
                    String[] strArray = spinner_allBankAccounts.getSelectedItem().toString().split(":");
                    String type =strArray[0];
                    String accountNumber = strArray[1];
                    databaseAccess.addMoneyToAccount(type,accountNumber,amount);
                    Toast toast = (Toast) Toast.makeText(getApplicationContext(), amount + " added to account: " + accountNumber, Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent(AddMoneyActivity.this, MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public void addItemsOnSpinnerAllAccounts() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, user.allAccounts);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_allBankAccounts.setAdapter(dataAdapter);

    }
}
