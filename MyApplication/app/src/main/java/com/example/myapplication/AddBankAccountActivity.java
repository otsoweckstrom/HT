package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AddBankAccountActivity extends AppCompatActivity {

    Spinner accountType;
    EditText et_balance;
    EditText et_savingsPer;
    EditText et_creditAmount;
    Button btn_createAccount;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_account);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        accountType = (Spinner) findViewById(R.id.spinner_accountType);
        et_balance = findViewById(R.id.et_balance);
        et_creditAmount = findViewById(R.id.et_creditAmount);
        et_savingsPer = findViewById(R.id.et_savingsPer);
        btn_createAccount = findViewById(R.id.button_createAccount);
        final DatabaseAccess databaseAccess = new DatabaseAccess(getApplicationContext());
        addItemsOnSpinnerAccountType();

        //Setting an onItemSelectedListener so non needed editTexts stay hidden when not needed
        accountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1 || position == 2 || position == 3) {
                    et_balance.setVisibility(View.VISIBLE);
                    if (position == 2) {
                        et_creditAmount.setVisibility(View.VISIBLE);
                        et_savingsPer.setVisibility(View.GONE);
                    } else if (position == 3) {
                        et_savingsPer.setVisibility(View.VISIBLE);
                        et_creditAmount.setVisibility(View.GONE);
                    }
                } else {
                    et_creditAmount.setVisibility(View.GONE);
                    et_savingsPer.setVisibility(View.GONE);
                    et_balance.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //button for creating an account
        btn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = accountType.getSelectedItem().toString();
                float balance = Float.parseFloat(et_balance.getText().toString());
                String accountNumber = "";

                try {
                    //Check which type of account the user wants to create
                    if (s.equalsIgnoreCase("Debit")) {
                        accountNumber = "FI";
                        accountNumber = accountNumber + randomInt(2) + " " + randomInt(4) + " " + randomInt(4) + " " + randomInt(2);
                        if (databaseAccess.createNewDebitAccount(accountNumber, user.getUserID(), balance) == true) {
                            Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Debit account created successfully", Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(AddBankAccountActivity.this, ManageAccountActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                            finish();
                        }
                    }
                    if (s.equalsIgnoreCase("Credit")) {
                        accountNumber = "FI";
                        accountNumber = accountNumber + randomInt(2) + " " + randomInt(4) + " " + randomInt(4) + " " + randomInt(2);
                        float creditAmount = Float.parseFloat(et_creditAmount.getText().toString());
                        if (databaseAccess.createNewCreditAccount(accountNumber, user.getUserID(), balance, creditAmount) == true) {
                            Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Credit account created successfully", Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(AddBankAccountActivity.this, ManageAccountActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                            finish();
                        }
                    }
                    if (s.equalsIgnoreCase("Savings")) {
                        accountNumber = "FI";
                        accountNumber = accountNumber + randomInt(2) + " " + randomInt(4) + " " + randomInt(4) + " " + randomInt(2);
                        float savingsPer = Float.parseFloat(et_savingsPer.getText().toString());
                        if (databaseAccess.createNewSavingsAccount(accountNumber, user.getUserID(), balance, savingsPer) == true) {
                            Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Savings account created successfully", Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(AddBankAccountActivity.this, ManageAccountActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                            finish();
                        }
                    }
                } catch (Exception e) {
                    Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Make sure all the boxes are correctly filled and try again", Toast.LENGTH_LONG);
                    toast.show();
                }


            }
        });

    }
    //Creates a random number parameter decides the length i = 1 returns 1-9, i = 2 returns 10-99 etc.
    static int randomInt(int i) {
        int bound = (int) Math.pow(10, i);
        int origin = (int) Math.pow(10, i - 1);
        int randomInt = ThreadLocalRandom.current().nextInt(origin, bound);
        return randomInt;
    }

    public void addItemsOnSpinnerAccountType() {

        List<String> accountType_array = new ArrayList<String>();
        accountType_array.add("Choose account type");
        accountType_array.add("Debit");
        accountType_array.add("Credit");
        accountType_array.add("Savings");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accountType_array);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountType.setAdapter(dataAdapter);
    }

}
