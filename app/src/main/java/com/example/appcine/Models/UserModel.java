package com.example.appcine.Models;

public class UserModel {
    private String name;
    private String mail;
    private String pass;

    public UserModel(String name, String mail, String pass) {
        this.name = name;
        this.mail = mail;
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "mail='" + mail + '\'' +
                ", pass=" + pass +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
