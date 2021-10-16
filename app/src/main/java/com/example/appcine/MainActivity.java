package com.example.appcine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout tilUser;
    private Button btnLogin, btnRegister;

    //SHARED PREFERENCES
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tilUser = (TextInputLayout) findViewById(R.id.til_user);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        //INICIALIZAMOS LAS VARIABLES SHARED
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedEditor = sharedPreferences.edit();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RegisterActivity.class);



                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DashboardActivity.class);

                String mailUser = tilUser.getEditText().getText().toString();
                mailUser = mailUser.split("@")[0];
                intent.putExtra("nameUser",mailUser);
                
                startActivity(intent);
            }
        });



    }
}