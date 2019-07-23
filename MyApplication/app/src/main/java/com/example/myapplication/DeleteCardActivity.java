package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteCardActivity extends AppCompatActivity {
    Button btn_deleteCard;
    User user;
    DatabaseAccess databaseAccess;
    Spinner spinner_cardType;
    Spinner spinner_debitCards;
    Spinner spinner_creditCards;
    int userID;
    String cardName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_card);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        userID = user.getUserID();
        databaseAccess = new DatabaseAccess(getApplicationContext());

        spinner_cardType = findViewById(R.id.spinner_cardType);
        spinner_creditCards = findViewById(R.id.spinner_creditCards);
        spinner_debitCards = findViewById(R.id.spinner_debitCards);
        btn_deleteCard = findViewById(R.id.btn_deleteCard);
        addItemsOnSpinnerCardType();
        addItemsOnSpinnerCreditCards();
        addItemsOnSpinnerDebitCards();

        btn_deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(spinner_cardType.getSelectedItemPosition() == 1){
                        cardName = spinner_debitCards.getSelectedItem().toString();
                        databaseAccess.deleteCard(cardName, 1);
                        Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Debit card deleted", Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(DeleteCardActivity.this, ManageAccountActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                    }else if(spinner_cardType.getSelectedItemPosition() == 2) {
                        cardName = spinner_creditCards.getSelectedItem().toString();
                        databaseAccess.deleteCard(cardName, 2);
                        Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Credit card deleted", Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(DeleteCardActivity.this, ManageAccountActivity.class);
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
        spinner_cardType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    spinner_debitCards.setVisibility(View.GONE);
                    spinner_creditCards.setVisibility(View.GONE);
                } else if (position == 1) {
                    spinner_debitCards.setVisibility(View.VISIBLE);
                    spinner_creditCards.setVisibility(View.GONE);
                } else if (position == 2) {
                    spinner_debitCards.setVisibility(View.GONE);
                    spinner_creditCards.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


    }

    //Gets all the cards the user has and sets the cardnumber on the spinner
    public void addItemsOnSpinnerCreditCards() {
        ArrayList<BankCard> creditCards = databaseAccess.getCreditCards(userID);
        ArrayList<String> allCards = new ArrayList<>();
        for (BankCard x : creditCards) {
            allCards.add(x.cardNumber);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, allCards);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_creditCards.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinnerDebitCards() {
        ArrayList<BankCard> debitCards = databaseAccess.getDebitCards(userID);
        ArrayList<String> allCards = new ArrayList<>();
        for (BankCard x : debitCards) {
            allCards.add(x.cardNumber);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, allCards);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_debitCards.setAdapter(dataAdapter);
    }

    //This sets the choices on the card type spinner
    public void addItemsOnSpinnerCardType() {

        List<String> accountType_array = new ArrayList<String>();
        accountType_array.add("Choose card type");
        accountType_array.add("Debit");
        accountType_array.add("Credit");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accountType_array);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cardType.setAdapter(dataAdapter);
    }
}
