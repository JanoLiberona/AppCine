package com.example.appcine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TitleActivity extends AppCompatActivity {
    TextView tvTitleName; //variable de la vista
    String titleName; //variable interna de la clase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        //referencias
        tvTitleName = (TextView) findViewById(R.id.tv_titleName);
        titleName = getIntent().getStringExtra("nameCommentedTitles");//este es declarado en la línea 66 de dashboardactivity.java

        tvTitleName.setText(titleName);
    }
}