package com.example.appcine.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.appcine.Adapters.CustomAdapter;
import com.example.appcine.Adapters.MovieAdapter;
import com.example.appcine.Models.LikedMovieModel;
import com.example.appcine.R;

import java.util.ArrayList;
import java.util.List;

public class LikedMoviesActivity extends AppCompatActivity {

    ArrayList<LikedMovieModel> movies;
    List<String> listMovies;
    RecyclerView recyclerView;
    Long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_movies);

        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        recyclerView = findViewById(R.id.rvLikeddMovies);

        listMovies = new ArrayList<String>();
        movies = LikedMovieModel.listLikedMovies(LikedMoviesActivity.this);
        CustomAdapter adapter = new CustomAdapter(this, movies);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CustomAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View itemview, int position) {

            }
        });
    }
}