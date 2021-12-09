package com.example.appcine.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appcine.Database.Entities.LikedMovieEntity;

import java.util.List;

@Dao
public interface LikedMovieDAO {
    @Insert
    void insert(LikedMovieEntity likedMovieEntity);

    @Query("SELECT * FROM likedMovies WHERE id = :id")
    LikedMovieEntity getLikedMovie(Long id);

    @Query("SELECT * FROM likedMovies")
    List<LikedMovieEntity> getAll();

    @Query("SELECT count(id) from likedMovies")
    int getDataCount();

    @Update
    void update(LikedMovieEntity likedMovieEntity);

    @Delete
    void delete(LikedMovieEntity likedMovieEntity);
}
