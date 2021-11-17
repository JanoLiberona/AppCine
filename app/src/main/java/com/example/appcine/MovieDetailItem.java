package com.example.appcine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetailItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_item);

        TextView titleName = findViewById(R.id.titleName);
        TextView titleId = findViewById(R.id.titleId);
        ImageView titleImage = findViewById(R.id.titleImage);

        Intent intent = getIntent();
        
    }
}