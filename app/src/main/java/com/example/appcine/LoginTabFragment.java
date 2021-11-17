package com.example.appcine;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginTabFragment extends Fragment {

    //Inicialización variables
    TextInputLayout tilUser, tilPass;
    TextView tvTitle;
    Button btnLogin;
    TextView forgetPass;
    ImageView iconEmail, iconPass;
    String mail, pass;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container,false);

        //referencias widgets
        tilUser = root.findViewById(R.id.til_user);
        tilPass = root.findViewById(R.id.til_pass);
        tvTitle = root.findViewById(R.id.textView);
        forgetPass = root.findViewById(R.id.tv_forgetPass);
        btnLogin = root.findViewById(R.id.btn_login);
        iconEmail = root.findViewById(R.id.iconEmail);
        iconPass = root.findViewById(R.id.iconPass);


        //Animaciones
        //Setteando el lugar donde comenzarán los widgets
        tilUser.setTranslationX(1000);
        tilPass.setTranslationX(1000);
        forgetPass.setTranslationX(1000);
        iconEmail.setTranslationX(-1000);
        iconPass.setTranslationX(-1000);
        tvTitle.setTranslationY(800);
        btnLogin.setTranslationY(800);

        //Difuminación
        tvTitle.setAlpha(0);


        //Animación a la posición original
        tilUser.animate().translationX(0).setDuration(800).setStartDelay(300).start();
        tilPass.animate().translationX(0).setDuration(800).setStartDelay(300).start();
        iconEmail.animate().translationX(0).setDuration(800).setStartDelay(300).start();
        iconPass.animate().translationX(0).setDuration(800).setStartDelay(300).start();
        forgetPass.animate().translationX(0).setDuration(800).setStartDelay(300).start();
        btnLogin.animate().translationY(0).setDuration(800).setStartDelay(300).start();
        tvTitle.animate().translationY(0).alpha(1).setDuration(700).setStartDelay(300).start();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validator validator = new Validator();
                mail = tilUser.getEditText().getText().toString();
                pass = tilPass.getEditText().getText().toString();
                if (validarDatos() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "mail: " + mail + ", Password: " + pass, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), Dashboard.class);
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
            tilUser.setError(getString(R.string.campo_nulo));
            count ++;
        } else {
            tilUser.setError(null);

            if (validator.checkMail(mail)) {
                tilUser.setError(null);
            } else {
                tilUser.setError(getString(R.string.error_login_correoFormat));
                count++;
            }
        }

        if (!validator.checkNull(pass)) {
            tilPass.setError(null);
            tilPass.setError(getString(R.string.campo_nulo));
            count++;
        } else {
            tilPass.setError(null);
        }

        return count;
    }

}