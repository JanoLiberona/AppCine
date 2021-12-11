package com.example.appcine.Models;

import android.content.ClipData;
import android.content.Context;

import com.example.appcine.Database.AppDatabase;
import com.example.appcine.Database.Entities.LikedMovieEntity;
import com.example.appcine.R;

import java.util.ArrayList;
import java.util.List;

public class LikedMovieModel {

    String movieid, name, img, rdate, overview;

    public LikedMovieModel(String movieid, String name, String img, String rdate, String overview) {
        this.movieid = movieid;
        this.name = name;
        this.img = img;
        this.rdate = rdate;
        this.overview = overview;
    }

    public LikedMovieModel() {
    }

    public String getMovieid() {
        return movieid;
    }

    public void setMovieid(String movieid) {
        this.movieid = movieid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public static ArrayList<LikedMovieModel> listLikedMovies(Context context){

        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        List<LikedMovieEntity> likedMovieEntities = database.likedMovieDAO().getAll();
        ArrayList<LikedMovieModel> likedMovieModels = new ArrayList<LikedMovieModel>();
        for(int i = 0; i < likedMovieEntities.size(); i++){
            LikedMovieEntity likedMovieEntity = likedMovieEntities.get(i);

            likedMovieModels.add(new LikedMovieModel(likedMovieEntity.getMovieId(), likedMovieEntity.getName(), likedMovieEntity.getImg(), likedMovieEntity.getRdate(), likedMovieEntity.getOverview()));
        }
        return likedMovieModels;
    }
}
