package com.example.appcine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    TextView txtNameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //OBTENEMOS VARIABLE DE NUETRA ACTIVIDAD ANTERIOR
        String nameUser = getIntent().getStringExtra("nameUser");
        //ASIGNAREMOS DICHA VARIABLE A EL TextView generado
        txtNameUser = (TextView) findViewById(R.id.nameUser);
        txtNameUser.setText(nameUser);


    }
}