package com.example.appcine.Database;

import androidx.fragment.app.FragmentActivity;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appcine.Database.DAO.UsersDAO;
import com.example.appcine.Database.Entities.UserEntity;

@Database(entities = {UserEntity.class}, version = 1)
public abstract  class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;

    public abstract UsersDAO usersDAO();

    public static AppDatabase getInstance(FragmentActivity context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class,"appCine-db").allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }

}
