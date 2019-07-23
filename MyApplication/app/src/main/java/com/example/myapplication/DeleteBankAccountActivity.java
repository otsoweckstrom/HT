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

public class DeleteBankAccountActivity extends AppCompatActivity {

    Spinner accountType;
    Button btn_deleteAccount;
    User user;
    DatabaseAccess databaseAccess;
    Spinner spinner_debitAccounts;
    Spinner spinner_creditAccounts;
    Spinner spinner_savingsAccounts;
    int userID;
    String accountName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_bank_account);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        userID = user.getUserID();
        databaseAccess = new DatabaseAccess(getApplicationContext());
        accountType = (Spinner) findViewById(R.id.spinner_accountTypeDelete);
        btn_deleteAccount = findViewById(R.id.btn_deleteAccount);
        spinner_debitAccounts = findViewById(R.id.spinner_debitAccounts);
        spinner_savingsAccounts = findViewById(R.id.spinner_savingsAccounts);
        spinner_creditAccounts = findViewById(R.id.spinner_creditAccounts);

        addItemsOnSpinnerAccountType();
        addItemsOnSpinnerCredit();
        addItemsOnSpinnerDebit();
        addItemsOnSpinnerSavings();

        //Button for deleting the account, a lot of stupid checks to get the right account and not break incase something is empty
        btn_deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try{
                        if(accountType.getSelectedItemPosition() == 1){
                            accountName = spinner_debitAccounts.getSelectedItem().toString();
                            databaseAccess.deleteBankAccount(accountName, 1);
                            Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Debit account deleted", Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(DeleteBankAccountActivity.this, ManageAccountActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                            finish();
                        }else if(accountType.getSelectedItemPosition() == 2){
                            accountName = spinner_creditAccounts.getSelectedItem().toString();
                            databaseAccess.deleteBankAccount(accountName, 2);
                            Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Credit account deleted", Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(DeleteBankAccountActivity.this, ManageAccountActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                            finish();
                        }else if(accountType.getSelectedItemPosition() == 3){
                            accountName = spinner_savingsAccounts.getSelectedItem().toString();
                            databaseAccess.deleteBankAccount(accountName, 3);
                            Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Savings account deleted", Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(DeleteBankAccountActivity.this, ManageAccountActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                            finish();
                        }
                    }catch (Exception e){
                        Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Deletion did not work", Toast.LENGTH_LONG);
                        toast.show();
                    }


            }
        });




        //Setting an onItemSelectedListener so non needed spinners stay hidden when not needed
        accountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    spinner_savingsAccounts.setVisibility(View.GONE);
                    spinner_creditAccounts.setVisibility(View.GONE);
                    spinner_debitAccounts.setVisibility(View.GONE);
                } else if (position == 1) {
                    spinner_debitAccounts.setVisibility(View.VISIBLE);
                    spinner_savingsAccounts.setVisibility(View.GONE);
                    spinner_creditAccounts.setVisibility(View.GONE);
                } else if (position == 2) {
                    spinner_creditAccounts.setVisibility(View.VISIBLE);
                    spinner_savingsAccounts.setVisibility(View.GONE);
                    spinner_debitAccounts.setVisibility(View.GONE);
                } else if (position == 3) {
                    spinner_savingsAccounts.setVisibility(View.VISIBLE);
                    spinner_creditAccounts.setVisibility(View.GONE);
                    spinner_debitAccounts.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


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


    public void addItemsOnSpinnerDebit() {
        ArrayList<DebitAccountClass> debitAccounts = databaseAccess.getDebitAccounts(userID);
        ArrayList<String> allAccounts = new ArrayList<>();
        for(DebitAccountClass x : debitAccounts){
            allAccounts.add(x.accountNumber);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, allAccounts);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_debitAccounts.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinnerCredit() {
        ArrayList<CreditAccountClass> creditAccounts = databaseAccess.getCreditAccounts(userID);
        ArrayList<String> allAccounts = new ArrayList<>();
        for(CreditAccountClass x : creditAccounts){
            allAccounts.add(x.accountNumber);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, allAccounts);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_creditAccounts.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinnerSavings() {

        ArrayList<SavingsAccountClass> savingsAccounts = databaseAccess.getSavingsAccounts(userID);
        ArrayList<String> allAccounts = new ArrayList<>();
        for(SavingsAccountClass x : savingsAccounts){
            allAccounts.add(x.accountNumber);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, allAccounts);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_savingsAccounts.setAdapter(dataAdapter);
    }
}
