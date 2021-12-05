package com.example.appcine.Fragments;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appcine.Database.AppDatabase;
import com.example.appcine.Views.Dashboard;
import com.example.appcine.R;
import com.example.appcine.Helpers.Validate;
import com.example.appcine.Views.MainActivity;
import com.google.android.material.textfield.TextInputLayout;

public class LoginTabFragment extends Fragment {

    //Inicialización variables
    EditText tilUser, tilPass;
    TextView tvTitle, forgetPass;
    Button btnLogin;
    ImageView iconEmail, iconPass;
    String mail, pass;
    CardView cvLogin;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container,false);

        //referencias widgets
        tilUser = root.findViewById(R.id.etLoginUserEmail);
        tilPass = root.findViewById(R.id.etLoginUserPassword);
        tvTitle = root.findViewById(R.id.textView);
        forgetPass = root.findViewById(R.id.tv_forgetPass);
        btnLogin = root.findViewById(R.id.btn_login);
        iconEmail = root.findViewById(R.id.iconEmail);
        iconPass = root.findViewById(R.id.iconPass);
        cvLogin = root.findViewById(R.id.cv_login);

        //Animaciones
        btnLogin.setTranslationY(800);
        cvLogin.setTranslationY(800);

        btnLogin.setAlpha(0);
        cvLogin.setAlpha(0);

        btnLogin.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        cvLogin.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedEditor = sharedPreferences.edit();

        String existMailUser = sharedPreferences.getString("mailUser","");
        String existpassUser = sharedPreferences.getString("passUser","");
        if(!existMailUser.equals("") && !existpassUser.equals("")){
            tilUser.setText(existMailUser);
            tilPass.setText(existpassUser);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validate validate = new Validate();
                String mail = tilUser.getText().toString();
                String pass = tilPass.getText().toString();
                if (validarDatos() == 0) {
                    AppDatabase database = AppDatabase.getInstance(getActivity());
                    if (database.usersDAO().login(pass, mail).size() == 1) {
                        Long idUser = database.usersDAO().login(pass, mail).get(0).getId();
                        sharedEditor.putString("idUser", idUser.toString());
                        sharedEditor.putString("mailUser", mail);
                        sharedEditor.putString("passUser", pass);
                        sharedEditor.commit();
                        sharedEditor.apply();
                        Intent intent = new Intent(view.getContext(), Dashboard.class);
                        intent.putExtra("mail", mail);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                } else {
                    tilUser.setError("Correo o contraseña son inválidos");
                    tilPass.setError("Correo o contraseña son inválidos");
                }
            }
        });
        return root;
    }

    public int validarDatos() {
        Validate validate = new Validate();
        int count = 0;
        String mail = tilUser.getText().toString();
        String pass = tilPass.getText().toString();

        if (validate.checkNull(mail)) {
            if (validate.checkMail(mail)) {
                tilUser.setError(null);
            } else {
                tilUser.setError(getString(R.string.error_login_correoFormat));
                count++;
            }
        } else {
            tilUser.setError(getString(R.string.campo_nulo));
            count++;
        }

        if (validate.checkNull(pass)) {
            tilPass.setError(null);
        } else {
            tilPass.setError(getString(R.string.campo_nulo));
            count++;
        }
        return count;
    }

}