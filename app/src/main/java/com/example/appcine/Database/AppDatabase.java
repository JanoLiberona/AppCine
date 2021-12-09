package com.example.appcine.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appcine.Database.DAO.CommentDAO;
import com.example.appcine.Database.DAO.LikedMovieDAO;
import com.example.appcine.Database.DAO.UsersDAO;
import com.example.appcine.Database.Entities.CommentEntity;
import com.example.appcine.Database.Entities.LikedMovieEntity;
import com.example.appcine.Database.Entities.UserEntity;

@Database(entities = {UserEntity.class, LikedMovieEntity.class, CommentEntity.class}, version = 1)
public abstract  class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;

    public abstract UsersDAO usersDAO();
    public abstract LikedMovieDAO likedMovieDAO();
    public abstract CommentDAO commentDAO();


    public static AppDatabase getInstance(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class,"appCine-db").allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }

}
