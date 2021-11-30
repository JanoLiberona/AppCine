package com.example.appcine.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.appcine.R;

public class MovieDetailItem extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_item);

        setTitle("MovieDetailItem");
        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        TextView titleName = findViewById(R.id.titleName);
        //TextView titleId = findViewById(R.id.titleId);
        TextView titleRdate = findViewById(R.id.tvRdate);
        TextView titleoOverview = findViewById(R.id.tvOverview);
        ImageView titleImage = findViewById(R.id.titleImage);
        LottieAnimationView like = findViewById(R.id.lfLike);

        like.setOnClickListener(new View.OnClickListener() {
            boolean isAnimated = false;
            @Override
            public void onClick(View view) {
                if (!isAnimated) {
                    like.setSpeed(2f);
                    isAnimated = true;
                    like.playAnimation();
                } else {
                    like.setSpeed(-3F);
                    isAnimated = false;
                    like.playAnimation();
                }
            }
        });


        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        //String id = intent.getStringExtra("id");
        String img = intent.getStringExtra("poster_path");
        String rDate = intent.getStringExtra("release_date");
        String overview = intent.getStringExtra("overview");

        titleName.setText(title);
        //titleId.setText(id);
        titleRdate.setText(rDate);
        titleoOverview.setText(overview);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500"+img).into(titleImage);


    }
}