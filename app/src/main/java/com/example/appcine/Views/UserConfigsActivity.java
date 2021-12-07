package com.example.appcine.Views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appcine.Database.AppDatabase;
import com.example.appcine.Database.Entities.UserEntity;
import com.example.appcine.Helpers.Validate;
import com.example.appcine.R;

import java.util.List;

public class UserConfigsActivity extends AppCompatActivity {

    ImageView userAvatarImage;
    EditText userName, userMail, userBday, userPass;
    Button edit, save;
    ImageButton newUserImage;
    static int PReqCode = 1;
    static int REQUESTCODE = 1;
    Uri pickedImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_configs);

        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        //Scroll when select a editText
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        userAvatarImage = findViewById(R.id.ivUserProfileImage);
        userName = findViewById(R.id.etUserConfigName);
        userMail = findViewById(R.id.etUserConfigEmail);
        userBday = findViewById(R.id.etUserConfigBday);
        userPass = findViewById(R.id.etUserConfigPass);
        edit = findViewById(R.id.btnConfigEdit);
        save = findViewById(R.id.btnConfigSave);
        newUserImage = findViewById(R.id.iBtnAddUserImage);

        //Selección de una foto desde la galeria
        newUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 21) {
                    checkAndRequestforPermission();
                } else {
                    openGallery();
                }
            }
        });

        //Obteniendo los datos de la base datos.
        AppDatabase database = AppDatabase.getInstance(UserConfigsActivity.this);
        Long idUser = database.usersDAO().getAll().get(0).getId();
        String user = database.usersDAO().getUser(idUser).get(0).getName();
        String mail = database.usersDAO().getUser(idUser).get(0).getMail();
        String bday = database.usersDAO().getUser(idUser).get(0).getBday();
        String pass = database.usersDAO().getUser(idUser).get(0).getPassword();

        //settea los datos traidos de la base de datos y los campos quedan no disponibles para ser escritos
        userName.setFocusable(false);
        userName.setText(user);
        userMail.setFocusable(false);
        userMail.setText(mail);
        userBday.setFocusable(false);
        userBday.setText(bday);
        userPass.setFocusable(false);
        userPass.setText(pass);

        //Botón edit permite que los edit text puedan ser editados
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName.setFocusableInTouchMode(true);
                userMail.setFocusableInTouchMode(true);
                userBday.setFocusableInTouchMode(true);
                userPass.setFocusableInTouchMode(true);
            }
        });

        //Botón guardar actualiza los datos que se modifiquen
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
                    UserEntity userEntity = database.usersDAO().getUser(idUser).get(0);
                    userEntity.setName(editedUser);
                    userEntity.setMail(editedMail);
                    userEntity.setPassword(editedPass);
                    userEntity.setBday(editedBday);
                    database.usersDAO().update(userEntity);
                }
            }
        });
    }



    /* ** Metodos ** */

    //Método para preguntar y aceptar los permisos para la galería
    private void checkAndRequestforPermission() {
        if (ContextCompat.checkSelfPermission(UserConfigsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(UserConfigsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(UserConfigsActivity.this, "Para cambiar tu foto, debes aceptar los permisos", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(UserConfigsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        } else {
            openGallery();
        }
    }

    //Método para abrir la galería
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null) {
            //El usuario ha seleccionado una foto de manera correcta
            //Guardamos la foto
            pickedImgUri = data.getData();
            userAvatarImage.setImageURI(pickedImgUri);
        }
    }

    //Validación para que los campos no queden nulos y tengan el formato correspondiente
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