package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeUsernameActivity extends AppCompatActivity {

    User user;
    DatabaseAccess databaseAccess;
    Button btn_change;
    EditText et_changeusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        databaseAccess = new DatabaseAccess(getApplicationContext());

        et_changeusername = findViewById(R.id.et_changeusername);
        btn_change = findViewById(R.id.btn_change);

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String new_name = et_changeusername.getText().toString();
                    databaseAccess.changeUserName(new_name, user.getUserID());
                    Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Username changed to " + new_name, Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent(ChangeUsernameActivity.this, ManageAccountActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    Toast toast = (Toast) Toast.makeText(getApplicationContext(), "The username is already taken", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
    }
}
