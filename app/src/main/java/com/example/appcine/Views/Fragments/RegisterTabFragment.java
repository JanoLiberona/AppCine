package com.example.appcine.Views.Fragments;

import static android.app.Activity.RESULT_OK;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appcine.Database.AppDatabase;
import com.example.appcine.Database.Entities.UserEntity;
import com.example.appcine.Views.AvatarSelectionActivity;
import com.example.appcine.Views.PreferencesActivity;
import com.example.appcine.R;
import com.example.appcine.Helpers.Validate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterTabFragment extends Fragment {

    Button btnRegister;
    EditText til_user, til_correo, til_contrasena, til_RContrasena, til_bday;
    CheckBox chkAccept;
    int mDay, mMonth, mYear;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    ProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.register_tab_fragment, container, false);

        til_user = root.findViewById(R.id.etUserName);
        til_correo = root.findViewById(R.id.etUserEmail);
        til_contrasena = root.findViewById(R.id.etUserPass);
        til_RContrasena = root.findViewById(R.id.etUserRePass);
        til_bday = root.findViewById(R.id.etUserBday);
        chkAccept = root.findViewById(R.id.chkTerminos);
        btnRegister = root.findViewById(R.id.btnRegistrar);
        progressBar = root.findViewById(R.id.pbRegisterProgressBar);

        progressBar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


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
                    Map<String, Object> usuario = new HashMap<>();
                    usuario.put("userName", user);
                    usuario.put("birthDay", bday);

                    if (chkAccept.isChecked()) {
                        createUserAccount(mail, user, pass);
                        progressBar.setVisibility(View.VISIBLE);
                        db.collection("users").add(usuario).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showMessage("No se pudieron agregar algunos datos a la base de datos: \n" + e);
                            }
                        });
                        Intent intent = new Intent(view.getContext(), AvatarSelectionActivity.class);
                        intent.putExtra("user", user);
                        intent.putExtra("mail", mail);
                        intent.putExtra("bday", bday);
                        intent.putExtra("pass", pass);
                        progressBar.setVisibility(View.GONE);
                        startActivity(intent);
                        Animatoo.animateSwipeLeft(getActivity());
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


    //Método para crear usuario en firebase con firebase auth
    private void createUserAccount(String uemail, final String uname, String password) {
        mAuth.createUserWithEmailAndPassword(uemail, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                } else {
                    showMessage("Creación de cuenta falló: "+ task.getException().getMessage());
                }
            }
        });
    }


    //Para mostrar mensaje toast
    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

}