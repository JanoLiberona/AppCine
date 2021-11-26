package com.example.appcine;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

public class LoginTabFragment extends Fragment {

    //Inicialización variables
    TextInputLayout tilUser, tilPass;
    TextView tvTitle, forgetPass;
    Button btnLogin;
    ImageView iconEmail, iconPass;
    String mail, pass;
    CardView cvLogin;

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
        cvLogin = root.findViewById(R.id.cv_login);

        //Animaciones
        btnLogin.setTranslationY(800);
        cvLogin.setTranslationY(1000);

        btnLogin.setAlpha(0);
        cvLogin.setAlpha(0);

        btnLogin.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        cvLogin.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validate validate = new Validate();
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
        Validate validate = new Validate();
        int count = 0;

        if (!validate.checkNull(mail)) {
            tilUser.setError(getString(R.string.campo_nulo));
            count ++;
        } else {
            tilUser.setError(null);

            if (validate.checkMail(mail)) {
                tilUser.setError(null);
            } else {
                tilUser.setError(getString(R.string.error_login_correoFormat));
                count++;
            }
        }

        if (!validate.checkNull(pass)) {
            tilPass.setError(null);
            tilPass.setError(getString(R.string.campo_nulo));
            count++;
        } else {
            tilPass.setError(null);
        }

        return count;
    }

}