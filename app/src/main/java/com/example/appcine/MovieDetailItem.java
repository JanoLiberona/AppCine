package com.example.appcine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailItem extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_item);

        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        TextView titleName = findViewById(R.id.titleName);
        TextView titleId = findViewById(R.id.titleId);
        ImageView titleImage = findViewById(R.id.titleImage);


        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String id = intent.getStringExtra("id");
        String img = intent.getStringExtra("poster_path");


        titleName.setText(title);
        titleId.setText(id);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500"+img).into(titleImage);

    }
}