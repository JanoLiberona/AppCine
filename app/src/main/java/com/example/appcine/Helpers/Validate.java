package com.example.appcine.Helpers;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Validate {

    //Validación contraseña
    public boolean checkPassLength(String pass) {
        if(pass.length() >= 8) {
            return true;
        } else {
            return false;
        }
    }

    //Validación correo
    public boolean checkMail(String mail) {
        return Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }

    //Validación posible nulo
    public boolean checkNull(String text) {
        if (text.equals("")){
            return false;
        } else {
            return true;
        }
    }
}
