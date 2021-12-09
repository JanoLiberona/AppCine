package com.example.appcine.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "likedMovies")
public class LikedMovieEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    Long id;

    @NonNull
    @ColumnInfo(name = "movieid")
    String movieId;

    @NonNull
    @ColumnInfo(name = "name")
    String name;

    @NonNull
    @ColumnInfo(name = "img")
    String img;

    @NonNull
    @ColumnInfo(name = "rdate")
    String rdate;

    @NonNull
    @ColumnInfo(name = "overview")
    String overview;

    public LikedMovieEntity(@NonNull String movieId, @NonNull String name, @NonNull String img, @NonNull String rdate, @NonNull String overview) {
        this.movieId = movieId;
        this.name = name;
        this.img = img;
        this.rdate = rdate;
        this.overview = overview;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(@NonNull String movieId) {
        this.movieId = movieId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getImg() {
        return img;
    }

    public void setImg(@NonNull String img) {
        this.img = img;
    }

    @NonNull
    public String getRdate() {
        return rdate;
    }

    public void setRdate(@NonNull String rdate) {
        this.rdate = rdate;
    }

    @NonNull
    public String getOverview() {
        return overview;
    }

    public void setOverview(@NonNull String overview) {
        this.overview = overview;
    }
}
