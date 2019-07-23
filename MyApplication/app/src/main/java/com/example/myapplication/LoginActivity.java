package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText et_name;
    EditText et_password;
    Button sign_in;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_name = findViewById(R.id.login_name);
        et_password = findViewById(R.id.login_password);
        sign_in = findViewById(R.id.buttonSignIn);
        register = findViewById(R.id.tv_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(et_name.getText().toString().isEmpty()) {
                    Toast toast = (Toast) Toast.makeText(getApplicationContext(), "please enter a login name", Toast.LENGTH_LONG);
                    toast.show();
                }
                if (et_password.getText().toString().isEmpty()) {
                        Toast toast = (Toast) Toast.makeText(getApplicationContext(), "please enter a password", Toast.LENGTH_LONG);
                        toast.show();
                } else {
                    DatabaseAccess databaseAccess = new DatabaseAccess(getApplicationContext());
                    databaseAccess.openDatabase();
                    String n = et_name.getText().toString();

                    if (databaseAccess.getLoginPassword(n).equals(et_password.getText().toString())) {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        User user = databaseAccess.createUser(n);
                        databaseAccess.closeDatabase();
                        intent.putExtra("user", user);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast toast = (Toast) Toast.makeText(getApplicationContext(), "incorrect credentials", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }

        });

    }

}
