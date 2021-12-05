package com.example.appcine.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "comments")
public class CommentEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    Long id;

    @NonNull
    @ColumnInfo(name = "uName")
    public String uName;

    @NonNull
    @ColumnInfo(name = "comment")
    String comment;

    public CommentEntity(@NonNull String uName, @NonNull String comment) {
        this.uName = uName;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getuName() {
        return uName;
    }

    public void setuName(@NonNull String uName) {
        this.uName = uName;
    }

    @NonNull
    public String getComment() {
        return comment;
    }

    public void setComment(@NonNull String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentEntity{" +
                "id=" + id +
                ", uName='" + uName + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
