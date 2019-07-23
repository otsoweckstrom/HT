package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class ManageAccountActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    User user;
    TextView tv_add_account;
    TextView tv_add_card;
    TextView tv_change_name;
    TextView tv_change_password;
    TextView tv_delete_account;
    TextView tv_delete_card;
    TextView tv_delete_user;
    Context context;
    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        //bring the user object into the activity
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        //Set the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        databaseAccess = new DatabaseAccess(getApplicationContext());

        context = this;
        //initialize the views
        tv_add_account = findViewById(R.id.tv_add_account);
        tv_add_card = findViewById(R.id.tv_add_card);
        tv_change_name = findViewById(R.id.tv_change_username);
        tv_change_password = findViewById(R.id.tv_change_password);
        tv_delete_account = findViewById(R.id.tv_delete_bank_account);
        tv_delete_card = findViewById(R.id.tv_delete_card);
        tv_delete_user = findViewById(R.id.tv_delete_user);


        //set on click listeners for the textviews so an activity can be chosen
        tv_add_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAccountActivity.this, AddBankAccountActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        tv_add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAccountActivity.this, AddCardActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        tv_change_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAccountActivity.this, ChangeUsernameActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        tv_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAccountActivity.this, ChangePasswordActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        tv_delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAccountActivity.this, DeleteBankAccountActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        tv_delete_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAccountActivity.this, DeleteCardActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        //Setting a confirmation dialog for user account deletion so the user doesn't do so on accident
        tv_delete_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete your account?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete user and send the app back to login screen
                        databaseAccess.deleteUserAccount(user.getUserID());
                        Intent intent = new Intent(ManageAccountActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(ManageAccountActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(ManageAccountActivity.this, MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_manage) {


        } else if (id == R.id.nav_payment) {
            Intent intent = new Intent(ManageAccountActivity.this, PaymentActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
