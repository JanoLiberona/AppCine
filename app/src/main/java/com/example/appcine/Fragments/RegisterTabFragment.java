package com.example.appcine.Fragments;

import static android.app.Activity.RESULT_OK;
import static android.view.View.INVISIBLE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appcine.Database.AppDatabase;
import com.example.appcine.Database.Entities.UserEntity;
import com.example.appcine.Models.User;
import com.example.appcine.Views.PreferencesActivity;
import com.example.appcine.R;
import com.example.appcine.Helpers.Validate;
import com.example.appcine.Views.UserConfigsActivity;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;

public class RegisterTabFragment extends Fragment {

    private Button btnRegister;
    private EditText til_user, til_correo, til_contrasena, til_RContrasena, til_bday;
    private CheckBox chkAccept;
    String userName, mail, pass, repass, bday;
    int mDay, mMonth, mYear;
    private FirebaseAuth mAuth;
    ImageView userImage;
    ImageButton newUserImage;
    static int PReqCode = 1;
    static int REQUESTCODE = 1;
    Uri pickedImgUri;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.register_tab_fragment, container, false);

        til_user = root.findViewById(R.id.etUserName);
        til_correo = root.findViewById(R.id.etUserEmail);
        til_contrasena = root.findViewById(R.id.etUserPass);
        til_RContrasena = root.findViewById(R.id.etUserRePass);
        til_bday = root.findViewById(R.id.etUserBday);
        chkAccept = root.findViewById(R.id.chkTerminos);
        btnRegister = root.findViewById(R.id.btnRegistrar);
        newUserImage = root.findViewById(R.id.iBtnaddUserAvatar);
        userImage = root.findViewById(R.id.userImage);

        mAuth = FirebaseAuth.getInstance();

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

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 21) {
                    checkAndRequestforPermission();
                } else {
                    openGallery();
                }
            }
        });

        //Calendar bday
        final Calendar calendar = Calendar.getInstance();
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mMonth = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);

        til_bday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        if (month + 1 >= 10) {
                            if (day + 1 > 10) {
                                til_bday.setText(day+"/"+(month+1)+"/"+year);
                            } else {
                                til_bday.setText("0"+day+"/"+(month + 1)+"/"+year);
                            }
                        } else {
                            if(day+1>10){
                                til_bday.setText(day+"/0"+(month+1)+"/"+year);
                            }
                            else{
                                til_bday.setText("0"+day+"/0"+(month+1)+"/"+year);
                            }
                        }
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user = til_user.getText().toString();
                final String mail = til_correo.getText().toString();
                final String pass = til_contrasena.getText().toString();
                final String bday = til_bday.getText().toString();
                if (validarDatos() == 0) {
                    AppDatabase database = AppDatabase.getInstance(getActivity());
                    UserEntity userEntity = new UserEntity(user, mail, bday, pass);
                    System.out.println(userEntity);
                    database.usersDAO().insert(userEntity);
                    List<UserEntity> users = database.usersDAO().getAll();

                    if (chkAccept.isChecked()) {
                        createUserAccount(mail, user, pass);
                        Intent intent = new Intent(view.getContext(), PreferencesActivity.class);
                        intent.putExtra("user", user);
                        intent.putExtra("mail", mail);
                        intent.putExtra("bday", bday);
                        intent.putExtra("pass", pass);
                        startActivity(intent);
                    }

                }
            }
        });

        return root;
    }

    //Validar editTexts
    public int validarDatos() {
        Validate validate = new Validate();
        String userName = til_user.getText().toString();
        String mail = til_correo.getText().toString();
        String pass = til_contrasena.getText().toString();
        String rpass = til_RContrasena.getText().toString();
        String bday = til_bday.getText().toString();

        int count = 0;

        //Evitar userName null
        if (validate.checkNull(userName)) {
            til_user.setError(null);
        } else {
            til_user.setError(getString(R.string.campo_nulo));
        }

        //Evitar mail nulo
        if (validate.checkNull(mail)){
            //Validación formato mail
            if (validate.checkMail(mail)) {
                til_correo.setError(null);
            } else {
                til_correo.setError(getString(R.string.error_login_correoFormat));
                count++;
            }
        } else {
            til_correo.setError(getString(R.string.campo_nulo));
            count++;
        }

        //Evitar pass null
        if (validate.checkNull(pass)){
            //Evitar contraseña menor a 8 caracteres
            if (validate.checkPassLength(pass)) {
                til_contrasena.setError(null);
            } else {
                til_contrasena.setError(getString(R.string.passLenght));
                count++;
            }
        } else {
            til_contrasena.setError(getString(R.string.campo_nulo));
            count++;
        }


        //Evitar rpass null
        if (validate.checkNull(rpass)){
            if (validate.checkPassLength(rpass)) {
                til_RContrasena.setError(null);
            } else {
                til_RContrasena.setError(getString(R.string.passLenght));
                count++;
            }
        } else {
            til_RContrasena.setError(getString(R.string.campo_nulo));
            count++;
        }

        //Comprobación contraseñas iguales
        if (rpass.equals(pass) && validate.checkNull(rpass) && validate.checkNull(pass)) {
            til_RContrasena.setError(null);
            til_contrasena.setError(null);
        } else if (validate.checkNull(rpass) && validate.checkNull(pass)){
            til_RContrasena.setError(getString(R.string.no_coinciden_pass));
            til_contrasena.setError(getString(R.string.no_coinciden_pass));
            count++;
        }

        //Evitar cumpleaños nulo
        if(validate.checkNull(bday)){
            til_bday.setError(null);
        } else {
            til_bday.setError(getString(R.string.campo_nulo));
            count++;
        }

        return count;
    }

    //Método para preguntar y aceptar los permisos para la galería
    private void checkAndRequestforPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(getActivity(), "Para cambiar tu foto, debes aceptar los permisos", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        } else {
            openGallery();
        }
    }

    //Método para crear usuario en firebase con firebase auth
    private void createUserAccount(String uemail, final String uname, String password) {

        mAuth.createUserWithEmailAndPassword(uemail, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    showMessage("Cuenta creada");
                    System.out.println("firebase method data: "+ uemail + "   "+ uname + "    " + password);
                    updateUserInfo(uname, pickedImgUri, mAuth.getCurrentUser());
                } else {
                    showMessage("Creación de cuenta falló: "+ task.getException().getMessage());
                }
            }
        });

    }

    //Método para abrir la galería
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null) {
            //El usuario ha seleccionado una foto de manera correcta
            //Guardamos la foto
            pickedImgUri = data.getData();
            userImage.setImageURI(pickedImgUri);
        }
    }

    //Para mostrar mensaje toast
    private void showMessage(String message) {

        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

    }

    //método para actualizar la foto y el nombre de usuario y cumpleaños
    private void updateUserInfo(final String name, Uri pickedImgUri, final FirebaseUser currentUser) {

        //subimos la imagen a firebase storage y obtenemos la url
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            showMessage("Registro completado");
                                        }

                                    }
                                });
                    }
                });

            }
        });

    }

}