package com.example.appcine;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Validate {

    //Validación contraseña
    public boolean checkPassLength(String pass) {
        boolean result = false;
        if(pass.length() > 8) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    //Validación correo
    public boolean checkMail(String mail) {
        return Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }

    //Validación posible nulo
    public boolean checkNull(String text) {
        if (text.equals("") || text.length() == 0 || text.trim().equals("")){
            return false;
        } else {
            return true;
        }
    }
}
