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

public class CardPaymentActivity extends AppCompatActivity {

    User user;
    int userID;
    DatabaseAccess databaseAccess;
    Button btn_makePayment;
    Spinner spinner_allCards;
    EditText et_payment;
    EditText et_receivingAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        userID = user.getUserID();
        databaseAccess = new DatabaseAccess(getApplicationContext());

        et_receivingAccount = findViewById(R.id.et_receivingAccount);
        btn_makePayment = findViewById(R.id.btn_makePayment);
        spinner_allCards = findViewById(R.id.spinner_allCards);
        et_payment = findViewById(R.id.et_payment);
        addItemsOnSpinnerAllAccounts();

        btn_makePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_payment.getText().toString().isEmpty() && !et_receivingAccount.getText().toString().isEmpty()){
                    float amount = Float.parseFloat(et_payment.getText().toString());
                    float payCeiling = 0;
                    String receivingAccount = et_receivingAccount.getText().toString();
                    String[] strArray = spinner_allCards.getSelectedItem().toString().split(":");
                    String type =strArray[0];
                    String accountNumber = strArray[2];
                    if(type.equalsIgnoreCase("debit")){
                        payCeiling = databaseAccess.getDebitAccountPayCeiling(accountNumber);
                    }else if(type.equalsIgnoreCase("credit")){
                        payCeiling = databaseAccess.getCreditAccountPayCeiling(accountNumber);
                    }
                    if(payCeiling > amount){
                        databaseAccess.makePayment(type, accountNumber, amount);
                        databaseAccess.addMoneyToAccount("debit",receivingAccount,amount);
                        Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Payment "+amount+" to account " + receivingAccount + "successful", Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(CardPaymentActivity.this, MainActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        });
    }

    public void addItemsOnSpinnerAllAccounts() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, user.allCards);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_allCards.setAdapter(dataAdapter);

    }
}
