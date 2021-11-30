package com.example.appcine.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appcine.Database.Entities.UserEntity;

import java.util.List;

@Dao
public interface UsersDAO {
    @Insert
    void insert(UserEntity userEntity);

    @Query("SELECT * FROM users WHERE password = :password AND mail = :mail")
    List<UserEntity> login(String password, String mail);

    @Query("SELECT * FROM users")
    List<UserEntity> getAll();

    @Update
    void update(UserEntity userEntity);

    @Delete
    void delete(UserEntity userEntity);
}
