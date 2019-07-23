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

public class AddCardActivity extends AppCompatActivity {

    User user;
    DatabaseAccess databaseAccess;
    Button btn_createCard;
    Spinner accountType;
    Spinner creditChoice;
    Spinner debitChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        databaseAccess = new DatabaseAccess(getApplicationContext());
        btn_createCard = findViewById(R.id.btn_createCard);
        accountType = findViewById(R.id.spinner_accountType);
        creditChoice = findViewById(R.id.spinner_creditChoices);
        debitChoice = findViewById(R.id.spinner_debitChoices);

        addItemsOnSpinnerAccountType();
        addItemsOnSpinnerDebitChoice();
        addItemsOnSpinnerCreditChoice();

        btn_createCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountNumber = "";

                if (accountType.getSelectedItemPosition() == 1){
                    accountNumber = debitChoice.getSelectedItem().toString();
                    if (databaseAccess.createNewDebitCard(accountNumber, "" + randomInt(8)) == true) {
                        Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Debit card created successfully", Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(AddCardActivity.this, ManageAccountActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Debit card creation failed", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else if (accountType.getSelectedItemPosition() == 2){
                    accountNumber = creditChoice.getSelectedItem().toString();
                    if (databaseAccess.createNewCreditCard(accountNumber, "" + randomInt(8)) == true) {
                        Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Credit card created successfully", Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(AddCardActivity.this, ManageAccountActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Credit card creation failed", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }

            }
        });

        //Setting an onItemSelectedListener so non needed spinners stay hidden when not needed
        accountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    creditChoice.setVisibility(View.GONE);
                    debitChoice.setVisibility(View.GONE);
                } else if (position == 1) {
                    creditChoice.setVisibility(View.GONE);
                    debitChoice.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    creditChoice.setVisibility(View.VISIBLE);
                    debitChoice.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
        accountType_array.add("Choose card type");
        accountType_array.add("Debit");
        accountType_array.add("Credit");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accountType_array);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountType.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinnerDebitChoice() {
        ArrayList<String> allDebitAccounts = new ArrayList<>();
        for (DebitAccountClass x : user.debitAccounts) {
            allDebitAccounts.add(x.accountNumber);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, allDebitAccounts);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        debitChoice.setAdapter(dataAdapter);

    }

    public void addItemsOnSpinnerCreditChoice() {
        ArrayList<String> allCreditAccounts = new ArrayList<>();
        for (CreditAccountClass x : user.creditAccounts) {
            allCreditAccounts.add(x.accountNumber);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, allCreditAccounts);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        creditChoice.setAdapter(dataAdapter);

    }
}
