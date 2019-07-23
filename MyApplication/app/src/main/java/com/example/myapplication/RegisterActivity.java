package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText register_name;
    EditText register_password;
    Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //EditTexts for the user to input credentials to sign up with
        register_name = findViewById(R.id.register_name);
        register_password = findViewById(R.id.register_password);
        register_btn = findViewById(R.id.buttonRegister);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = new DatabaseAccess(getApplicationContext());
                if(databaseAccess.newUserRegister(register_name.getText().toString(),register_password.getText().toString()) == true){
                    Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Thank you for registering", Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    //If the registration fails show a toast
                    Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Something went wrong, the username might be taken", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });



    }
}
