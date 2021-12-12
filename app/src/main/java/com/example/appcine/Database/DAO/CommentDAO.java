package com.example.appcine.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appcine.Database.Entities.CommentEntity;

import java.util.List;

@Dao
public interface CommentDAO {

    @Insert
    void insert(CommentEntity commentEntity);

    @Query("SELECT * FROM comments where id = :id")
    CommentEntity getComment(Long id);

    @Query("SELECT * FROM comments")
    List<CommentEntity> getAll();

    @Update
    void update(CommentEntity commentEntity);

    @Delete
    void delete(CommentEntity commentEntity);
}
