package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {


    User user;
    DatabaseAccess databaseAccess;
    Button btn_changePassword;
    EditText et_oldPassword;
    EditText et_newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        databaseAccess = new DatabaseAccess(getApplicationContext());

        btn_changePassword = findViewById(R.id.btn_changePassword);
        et_newPassword = findViewById(R.id.et_newPassword);
        et_oldPassword = findViewById(R.id.et_oldPassword);

        btn_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_oldPassword.getText().toString().isEmpty() && !et_newPassword.getText().toString().isEmpty()){
                    if(et_oldPassword.getText().toString().equalsIgnoreCase(user.getLoginPassword())){
                        try{
                            databaseAccess.changePassword(et_newPassword.getText().toString(), user.getUserID());
                            Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Password changed", Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(ChangePasswordActivity.this, ManageAccountActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                            finish();
                        }catch (Exception e){
                            Toast toast = (Toast) Toast.makeText(getApplicationContext(), "Password not changed make sure the password is atleast 6 characters", Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }
                }
            }
        });
    }
}
