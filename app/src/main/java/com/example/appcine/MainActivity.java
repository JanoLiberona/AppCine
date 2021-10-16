package com.example.appcine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;


import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {


    //Inicialización Variables
    private Button btnLogin, btnRegister;
    private TextInputLayout tilUser, tilPass;
    private ImageView iconEmail, iconPass;
    private Transition transition;
    public static final long DURATION_TRANSITION = 1000; //Esta constante es publica, debido a que la usaremos en otra actividad


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencias a widgets
        btnLogin    = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        tilUser     = findViewById(R.id.til_user);
        tilPass     = findViewById(R.id.til_pass);
        iconEmail   = findViewById(R.id.iconEmail);
        iconPass    = findViewById(R.id.iconPass);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });



        // ++ANIMACIONES++
        //Comienzan fuera de la pantalla
        tilUser.setTranslationX(1000);
        tilPass.setTranslationX(1000);
        iconEmail.setTranslationX(-1000);
        iconPass.setTranslationX(-1000);
        btnLogin.setTranslationY(800);
        btnRegister.setTranslationY(800);

        //Traslado a la posición original original
        tilUser.animate().translationX(0).setDuration(800).setStartDelay(300).start();
        tilPass.animate().translationX(0).setDuration(800).setStartDelay(300).start();
        iconEmail.animate().translationX(0).setDuration(800).setStartDelay(300).start();
        iconPass.animate().translationX(0).setDuration(800).setStartDelay(300).start();
        btnLogin.animate().translationY(0).setDuration(800).setStartDelay(300).start();
        btnRegister.animate().translationY(0).setDuration(800).setStartDelay(300).start();



    }

    //Método para animación de entrada a otro Layout Explode (Sin implementar, porque el profe no nos los pasó)
    public void onExplodeClicked(View view) {
        transition = new Explode();
        comenzarOtraActividad();
    }

    //Método para animación de entrada Slide
    public void onSlideClicked(View view) {
        transition = new Slide(Gravity.START);
        comenzarOtraActividad();
    }


    @SuppressWarnings("unchecked")
    private void comenzarOtraActividad() {
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

}