package com.example.appcine;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterTabFragment extends Fragment {

    private Button btnRegister;
    private TextInputLayout til_correo, til_contraseña, til_RContrasena, tilBday;
    private CheckBox chkAccept;
    String mail, pass, repass, bday;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.register_tab_fragment, container, false);

        til_correo = root.findViewById(R.id.til_correo);
        til_contraseña = root.findViewById(R.id.til_contraseña);
        til_RContrasena = root.findViewById(R.id.til_RContraseña);
        tilBday = root.findViewById(R.id.til_FdNacimiento);
        chkAccept = root.findViewById(R.id.chkTerminos);
        btnRegister = root.findViewById(R.id.btnRegistrar);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validator validator = new Validator();
                mail = til_correo.getEditText().getText().toString();
                pass = til_contraseña.getEditText().getText().toString();
                repass = til_RContrasena.getEditText().getText().toString();
                if (validarDatos() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "mail: " + mail + ", Password: " + pass, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), PreferencesActivity.class);
                    startActivity(intent);
                }
            }
        });

        return root;
    }


    public int validarDatos() {
        Validator validator = new Validator();
        int count = 0;

        if (!validator.checkNull(mail)) {
            til_correo.setError(getString(R.string.campo_nulo));
            count ++;
        } else {
            til_correo.setError(null);

            if (validator.checkMail(mail)) {
                til_correo.setError(null);
            } else {
                til_correo.setError(getString(R.string.error_login_correoFormat));
                count++;
            }
        }

        if (!validator.checkNull(pass)) {
            til_contraseña.setError(null);
            til_contraseña.setError(getString(R.string.campo_nulo));
            count++;
        } else {
            til_contraseña.setError(null);
        }

        if (!validator.checkNull(repass)) {
            til_RContrasena.setError(null);
            til_RContrasena.setError(getString(R.string.campo_nulo));
            count++;
        } else {
            til_RContrasena.setError(null);
        }
        return count;
    }

}