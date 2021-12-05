package com.example.appcine.Views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appcine.R;

public class MovieItem extends AppCompatActivity {

    LottieAnimationView like;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_item);

        /*like = findViewById(R.id.lfLike);

        like.setOnClickListener(new View.OnClickListener() {
            boolean isAnimated = false;
            @Override
            public void onClick(View view) {
                if (!isAnimated) {
                    like.setSpeed(3f);
                    isAnimated=true;
                    like.playAnimation();
                } else {
                    like.setSpeed(-1F);
                    isAnimated=false;
                    like.playAnimation();
                }
            }
        });*/
    }

}
