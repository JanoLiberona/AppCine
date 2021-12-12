package com.example.appcine.Views;

import androidx.annotation.NonNull;
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
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appcine.Database.AppDatabase;
import com.example.appcine.Database.Entities.UserEntity;
import com.example.appcine.Helpers.Validate;
import com.example.appcine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class UserConfigsActivity extends AppCompatActivity {

    ImageView userAvatarImage;
    EditText userName, userMail, userBday, userPass;
    Button edit, save, btnLikedMovies, btnPreferences;
    ImageButton newUserImage;
    TextView logout;
    static int PReqCode = 1;
    static int REQUESTCODE = 1;
    Uri pickedImgUri;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    FirebaseUser currentUser;
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_configs);

        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        //Scroll cuando se selecciona un edittext
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        userAvatarImage = findViewById(R.id.ivUserProfileImage);
        userName = findViewById(R.id.etUserConfigName);
        userMail = findViewById(R.id.etUserConfigEmail);
        userBday = findViewById(R.id.etUserConfigBday);
        userPass = findViewById(R.id.etUserConfigPass);
        edit = findViewById(R.id.btnConfigEdit);
        save = findViewById(R.id.btnConfigSave);
        newUserImage = findViewById(R.id.iBtnAddUserImage);
        logout = findViewById(R.id.tvCerrarSesion);
        btnLikedMovies = findViewById(R.id.btnLikedMovies);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        Glide.with(this).load(currentUser.getPhotoUrl()).into(userAvatarImage);


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

        userAvatarImage.setOnClickListener(new View.OnClickListener() {
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
        database = AppDatabase.getInstance(UserConfigsActivity.this);
        Long idUser = database.usersDAO().getAll().get(0).getId();
        String user = database.usersDAO().getUser(idUser).get(0).getName();
        String mail = database.usersDAO().getUser(idUser).get(0).getMail();
        String bday = database.usersDAO().getUser(idUser).get(0).getBday();
        String pass = database.usersDAO().getUser(idUser).get(0).getPassword();

        //Setteando los datos traidos de la base de datos y los campos quedan no disponibles para ser escritos
        userName.setFocusable(false);
        userName.setText(user);
        userMail.setFocusable(false);
        userMail.setText(mail);
        userBday.setFocusable(false);
        userBday.setText(bday);
        userPass.setFocusable(false);
        userPass.setText(pass);

        save.setVisibility(View.INVISIBLE);

        //Botón edit permite que los edit text puedan ser editados
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName.setFocusableInTouchMode(true);
                userName.requestFocus();
                userName.setSelection(userName.getText().length());
                userMail.setFocusableInTouchMode(true);
                userBday.setFocusableInTouchMode(true);
                userPass.setFocusableInTouchMode(true);
                save.setVisibility(View.VISIBLE);
                edit.setVisibility(View.INVISIBLE);
                showMessage(getString(R.string.editData));
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
                    updateUserName(userName.getText().toString());
                    currentUser.updateEmail(editedMail);
                    currentUser.updatePassword(editedPass);
                    UserEntity userEntity = database.usersDAO().getUser(idUser).get(0);
                    userEntity.setName(editedUser);
                    userEntity.setMail(editedMail);
                    userEntity.setPassword(editedPass);
                    userEntity.setBday(editedBday);
                    database.usersDAO().update(userEntity);
                    showMessage(getString(R.string.dataSavedSucces));
                    save.setVisibility(View.INVISIBLE);
                    edit.setVisibility(View.VISIBLE);
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(UserConfigsActivity.this, MainActivity.class);
                startActivity(intent);
                Animatoo.animateSlideRight(UserConfigsActivity.this);
            }
        });

        btnLikedMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserConfigsActivity.this, LikedMoviesActivity.class);
                startActivity(intent);
                Animatoo.animateSlideUp(UserConfigsActivity.this);
            }
        });

    }


    

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
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUESTCODE);
    }

    //método que obtiene desde la galería la foto y la setea en el widget de la imagen de usuario
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null) {
            //El usuario ha seleccionado una foto de manera correcta
            //Guardamos la foto
            pickedImgUri = data.getData();
            userAvatarImage.setImageURI(pickedImgUri);

            uploadImageToFirebase(userName.getText().toString(),pickedImgUri, mAuth.getCurrentUser());
        }
    }

    //Método para subir la imagen a la cuenta de usuario en firebase
    private void uploadImageToFirebase(final String name, Uri pickedImgUri, final FirebaseUser currentUser) {
        StorageReference sReference = storageReference.child("users/"+currentUser.getUid()+"/profile.jpg");
        sReference.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();

                        Glide.with(UserConfigsActivity.this).load(uri).into(userAvatarImage);


                        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                showMessage(getString(R.string.imageUpdated));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showMessage("No se pudo actualizar la imagen");
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showMessage(getString(R.string.imageUpdatedFailure));
            }
        });
    }

    public void updateUserName(String uname) {
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(uname).build();

        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    showMessage("Datos actualizados");
                }
            }
        });
    }

    //Para mostrar mensaje toast
    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
            //Evitar contraseña menor a 8 caracteres
            if (validate.checkPassLength(pass)) {
                userPass.setError(null);
            } else {
                userPass.setError(getString(R.string.passLenght));
                count++;
            }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(UserConfigsActivity.this, Dashboard.class);
        startActivity(intent);
        Animatoo.animateSlideLeft(UserConfigsActivity.this);
    }
}