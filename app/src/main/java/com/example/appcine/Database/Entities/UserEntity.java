package com.example.appcine.Database.Entities;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    Long id;

    @NonNull
    @ColumnInfo(name = "mail")
    String mail;

    @NonNull
    @ColumnInfo(name = "password")
    String password;

    @NonNull
    @ColumnInfo(name = "bday")
    String bday;

    public UserEntity(@NonNull String mail, @NonNull String bday, @NonNull String password) {
        this.mail = mail;
        this.bday = bday;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getMail() {
        return mail;
    }

    public void setMail(@NonNull String mail) {
        this.mail = mail;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getBday() {
        return bday;
    }

    public void setBday(@NonNull String bday) {
        this.bday = bday;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                ", bday='" + bday + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
