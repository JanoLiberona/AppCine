package com.example.appcine.Fragments;

import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcine.Database.AppDatabase;
import com.example.appcine.Database.Entities.UserEntity;
import com.example.appcine.Models.User;
import com.example.appcine.Views.PreferencesActivity;
import com.example.appcine.R;
import com.example.appcine.Helpers.Validate;
import com.example.appcine.Views.UserConfigsActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;

public class RegisterTabFragment extends Fragment {

    private Button btnRegister;
    private EditText til_user, til_correo, til_contrasena, til_RContrasena, til_bday;
    private CheckBox chkAccept;
    String userName, mail, pass, repass, bday;
    int mDay, mMonth, mYear;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.register_tab_fragment, container, false);

        til_user = root.findViewById(R.id.etUserName);
        til_correo = root.findViewById(R.id.etUserEmail);
        til_contrasena = root.findViewById(R.id.etUserPass);
        til_RContrasena = root.findViewById(R.id.etUserRePass);
        til_bday = root.findViewById(R.id.etUserBday);
        chkAccept = root.findViewById(R.id.chkTerminos);
        btnRegister = root.findViewById(R.id.btnRegistrar);

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
                String user = til_user.getText().toString();
                String mail = til_correo.getText().toString();
                String pass = til_contrasena.getText().toString();
                String bday = til_bday.getText().toString();
                if (validarDatos() == 0) {
                    AppDatabase database = AppDatabase.getInstance(getActivity());
                    UserEntity userEntity = new UserEntity(user, mail, bday, pass);
                    System.out.println(userEntity);
                    database.usersDAO().insert(userEntity);
                    List<UserEntity> users = database.usersDAO().getAll();
                    for (int i = 0; i < users.size(); i++) {
                        System.out.println(users.get(i).toString());
                    }
                    if (chkAccept.isChecked()) {
                        Intent intent = new Intent(view.getContext(), PreferencesActivity.class);
                        intent.putExtra("user", user);
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
            til_contrasena.setError(null);
        } else {
            til_contrasena.setError(getString(R.string.campo_nulo));
            count++;
        }

        //Evitar rpass null
        if (validate.checkNull(rpass)){
            til_RContrasena.setError(null);
        } else {
            til_RContrasena.setError(getString(R.string.campo_nulo));
            count++;
        }

        //Comprobación contraseñas iguales
        if (rpass.equals(pass) && validate.checkNull(rpass) && validate.checkNull(pass)){
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
}