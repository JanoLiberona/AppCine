package com.example.appcine.Views.Fragments;

import androidx.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appcine.Database.AppDatabase;
import com.example.appcine.Views.Dashboard;
import com.example.appcine.R;
import com.example.appcine.Helpers.Validate;
import com.example.appcine.Views.ForgotPassActivity;
import com.example.appcine.Views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginTabFragment extends Fragment {

    //Inicialización variables
    EditText tilUser, tilPass;
    TextView tvTitle, forgetPass;
    Button btnLogin;
    ImageView iconEmail, iconPass;
    CardView cvLogin;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

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
        progressBar = root.findViewById(R.id.pbLoginProgressBar);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Intent intent = new Intent(getContext(), Dashboard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Animatoo.animateShrink(getActivity());
        }

        progressBar.setVisibility(View.INVISIBLE);

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
                btnLogin.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                Validate validate = new Validate();
                String mail = tilUser.getText().toString();
                String pass = tilPass.getText().toString();
                if (validarDatos() == 0) {

                    mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                Intent intent = new Intent(view.getContext(), Dashboard.class);
                                intent.putExtra("mail", mail);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                Animatoo.animateShrink(getActivity());
                            } else {
                                showMessage(task.getException().getMessage());
                            }
                        }
                    });

                } else {
                    btnLogin.setVisibility(View.VISIBLE);
                }
            }
        });

        /** Funcion deshabilitada para la entrega de la solemne, ya que la recuperación de la contraseña no se puede setear en la BD local**/
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Función deshabilitada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ForgotPassActivity.class);
                startActivity(intent);
                Animatoo.animateSlideUp(getActivity());
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

    private void showMessage(String text) {
        Toast.makeText(getActivity(),text,Toast.LENGTH_LONG).show();
    }
}