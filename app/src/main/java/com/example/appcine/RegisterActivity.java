package com.example.appcine;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    //Inicialización variables
    private Button btnRegister;
    private TextInputLayout tilUser, tilPass, tilRpass, tilBday;
    private ImageView iconEmail, iconPass, iconRpass, iconBday;
    private CheckBox chkAccept;
    private Button getBtnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Animación de entrada (Sin implementar, porque el profe no nos los pasó)
        Slide slide = new Slide(Gravity.START);
        slide.setDuration(MainActivity.DURATION_TRANSITION);
        slide.setInterpolator(new DecelerateInterpolator());
        getWindow().setEnterTransition(slide);

        //Referencias a widgets
        btnRegister = findViewById(R.id.btnRegistrar);

        //Intents
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PreferencesActivity.class);
                startActivity(intent);
            }
        });

    }
}