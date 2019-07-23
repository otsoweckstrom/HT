package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tw_accounts;
    TextView tw_cards;
    int userID;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bringing the user object into the activity
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        DatabaseAccess databaseAccess = new DatabaseAccess(getApplicationContext());
        userID = user.getUserID(); //Setting int userID to match the current users ID

        //Navigation toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Greeting message
        TextView tw_greeting = findViewById(R.id.tw_greeting);
        tw_greeting.setText("Greetings " + user.getLoginName());




        //creating and populating the arraylists that hold account objects
        user.setDebitAccounts(databaseAccess.getDebitAccounts(userID));
        user.creditAccounts = databaseAccess.getCreditAccounts(userID);
        user.savingsAccounts = databaseAccess.getSavingsAccounts(userID);
        user.creditCards = databaseAccess.getCreditCards(userID);
        user.debitCards = databaseAccess.getDebitCards(userID);
        setAllAccounts();
        setAllCards();
        //Textview for showing the users accounts
        tw_accounts = findViewById(R.id.tw_accounts);
        for(String x : user.allAccounts){
            tw_accounts.append(x.replaceAll(":"," ") + "\n");
        }
        tw_cards = findViewById(R.id.tw_cards);
        for(String x : user.allCards){
            tw_cards.append(x.replaceAll(":"," ") + "\n");
        }

    }

    //Populating the array that holds all the accounts the user has
    public void setAllAccounts(){
        user.setAllAccounts(new ArrayList<String>());
        for (DebitAccountClass x : user.getDebitAccounts()) {
            String info = x.getType() + ":" + x.getAccountNumber() + ":" + x.getBalance() + "€";
            user.getAllAccounts().add(info);
        }
        for(CreditAccountClass x : user.creditAccounts){
            user.allAccounts.add(x.getType() + ":" + x.getAccountNumber() + ":" + x.getBalance() + "€");
        }

        for (SavingsAccountClass x : user.savingsAccounts) {
            user.allAccounts.add(x.getType() + ":" + x.getAccountNumber() + ":" + x.getBalance() + "€");
        }
    }
    //Same for cards
    public void setAllCards(){
        user.setAllCards(new ArrayList<String>());
        for (BankCard x : user.debitCards) {
            user.allCards.add(x.getType() + ":" + x.getCardNumber() + ":" + x.getAccountNumber());
        }
        for (BankCard x : user.creditCards) {
            user.allCards.add(x.getType() + ":" + x.getCardNumber() + ":" + x.getAccountNumber());
        }
    }

    //Back press on the mainactivity "logs the user out"
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
        }

        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this, ManageAccountActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_payment) {
            Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
