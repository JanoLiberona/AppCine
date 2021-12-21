package com.example.appcine.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appcine.Adapters.CustomAdapter;
import com.example.appcine.Adapters.MovieAdapter;
import com.example.appcine.Database.AppDatabase;
import com.example.appcine.Models.LikedMovieModel;
import com.example.appcine.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LikedMoviesActivity extends AppCompatActivity {

    ArrayList<LikedMovieModel> movies;
    List<String> listMovies;
    RecyclerView recyclerView;
    TextView likedMoviesCounter;
    Integer moviesCounter;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_movies);

        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        recyclerView = findViewById(R.id.rvLikeddMovies);
        likedMoviesCounter = findViewById(R.id.tvLikedMoviesCounter);

        AppDatabase database = AppDatabase.getInstance(LikedMoviesActivity.this);
        moviesCounter = database.likedMovieDAO().getDataCount();
        likedMoviesCounter.setText(""+moviesCounter);

        listMovies = new ArrayList<String>();
        movies = LikedMovieModel.listLikedMovies(LikedMoviesActivity.this);
        CustomAdapter adapter = new CustomAdapter(this, movies);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CustomAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View itemview, int position) {
                String title = movies.get(position).getName();
                String movieid = movies.get(position).getMovieid();
                String img = movies.get(position).getImg();
                String rdate = movies.get(position).getRdate();
                String overview = movies.get(position).getOverview();
                Intent intent = new Intent(LikedMoviesActivity.this, MovieDetailItem.class);
                intent.putExtra("title", title);
                intent.putExtra("id", movieid);
                intent.putExtra("poster_path", img);
                intent.putExtra("release_date", rdate);
                intent.putExtra("overview", overview);
                startActivity(intent);
                Animatoo.animateInAndOut(LikedMoviesActivity.this);
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideDown(LikedMoviesActivity.this);
    }
}