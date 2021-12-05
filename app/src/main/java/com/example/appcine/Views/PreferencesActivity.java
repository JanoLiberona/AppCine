package com.example.appcine.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.appcine.R;

public class PreferencesActivity extends AppCompatActivity {

    private Button btnPreferences;
    private TextView tvSkip, tvUserName;
    private CheckBox comedia, historia, cFinccion, romance, thriller, misterio, drama, horror, accion, guerra, musical, sHeroes, animacion, rMovie, fantasia, deportes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        btnPreferences = findViewById(R.id.btnRegPreferences);
        tvSkip = findViewById(R.id.txtSkip);
        tvUserName = findViewById(R.id.tvUserName);

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        tvUserName.setText(" "+user);


        btnPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Dashboard.class);
                startActivity(intent);
            }
        });


        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Dashboard.class);
                startActivity(intent);
            }
        });


    }
}