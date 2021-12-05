package com.example.appcine.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.appcine.Database.AppDatabase;
import com.example.appcine.Database.Entities.UserEntity;
import com.example.appcine.Helpers.Validate;
import com.example.appcine.R;

public class UserConfigsActivity extends AppCompatActivity {

    EditText userName, userMail, userBday, userPass;
    Button edit, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_configs);

        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        //Scroll when select a editText
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        userName = findViewById(R.id.etUserConfigName);
        userMail = findViewById(R.id.etUserConfigEmail);
        userBday = findViewById(R.id.etUserConfigBday);
        userPass = findViewById(R.id.etUserConfigPass);
        edit = findViewById(R.id.btnConfigEdit);
        save = findViewById(R.id.btnConfigSave);

        AppDatabase database = AppDatabase.getInstance(UserConfigsActivity.this);
        Long idUser = database.usersDAO().getAll().get(0).getId();
        String user = database.usersDAO().getUser(idUser).get(0).getName();
        String mail = database.usersDAO().getUser(idUser).get(0).getMail();
        String bday = database.usersDAO().getUser(idUser).get(0).getBday();
        String pass = database.usersDAO().getUser(idUser).get(0).getPassword();
        System.out.println(mail);

        userName.setFocusable(false);
        userName.setText(user);
        userMail.setFocusable(false);
        userMail.setText(mail);
        userBday.setFocusable(false);
        userBday.setText(bday);
        userPass.setFocusable(false);
        userPass.setText(pass);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName.setFocusableInTouchMode(true);
                userMail.setFocusableInTouchMode(true);
                userBday.setFocusableInTouchMode(true);
                userPass.setFocusableInTouchMode(true);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName.setFocusable(false);
                userMail.setFocusable(false);
                userBday.setFocusable(false);
                userPass.setFocusable(false);
                String editedUser = userName.getText().toString();
                String editedMail = userMail.getText().toString();
                String editedPass = userPass.getText().toString();
                String editedBday = userBday.getText().toString();
                if (validarDatos() == 0) {
                    //AppDatabase database = AppDatabase.getInstance(UserConfigsActivity.this);
                    UserEntity userEntity = database.usersDAO().getAll().get(0);
                    userEntity.setName(editedUser);
                    userEntity.setMail(editedMail);
                    userEntity.setPassword(editedPass);
                    userEntity.setBday(editedBday);
                    database.usersDAO().update(userEntity);
                    System.out.println(userEntity);
                }
            }
        });
    }

    public int validarDatos() {
        Validate validate = new Validate();
        String name = userName.getText().toString();
        String mail = userMail.getText().toString();
        String pass = userPass.getText().toString();
        String bday = userBday.getText().toString();

        int count = 0;

        //Evitar userName null
        if (validate.checkNull(name)) {
            userName.setError(null);
        } else {
            userName.setError(getString(R.string.campo_nulo));
        }

        //Evitar mail nulo
        if (validate.checkNull(mail)){
            //Validación formato mail
            if (validate.checkMail(mail)) {
                userMail.setError(null);
            } else {
                userMail.setError(getString(R.string.error_login_correoFormat));
                count++;
            }
        } else {
            userMail.setError(getString(R.string.campo_nulo));
            count++;
        }

        //Evitar pass null
        if (validate.checkNull(pass)){
            userPass.setError(null);
        } else {
            userPass.setError(getString(R.string.campo_nulo));
            count++;
        }

        //Evitar cumpleaños nulo
        if(validate.checkNull(bday)){
            userBday.setError(null);
        } else {
            userBday.setError(getString(R.string.campo_nulo));
            count++;
        }


        return count;
    }
}