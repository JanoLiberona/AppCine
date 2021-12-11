package com.example.appcine.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.appcine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {

    Button btnforgotPass;
    EditText etForgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);


        btnforgotPass = findViewById(R.id.btnForgotPass);
        etForgotPass = findViewById(R.id.itForgotPass);

        btnforgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });


    }

    public void validate() {

        String email = etForgotPass.getText().toString();

        if (email.equals("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etForgotPass.setError(getString(R.string.error_login_correoFormat));
            return;
        }

        sendEmail(email);
    }

    public void sendEmail(String email) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String emailAddress = email;

        mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    showMessage(getString(R.string.correoEnviado));
                    Intent intent = new Intent(ForgotPassActivity.this, MainActivity.class);
                    startActivity(intent);
                    Animatoo.animateSlideDown(ForgotPassActivity.this);
                    finish();
                } else {
                    showMessage(getString(R.string.correoInvalido));
                }
            }
        });
    }

    //Para mostrar mensaje toast
    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ForgotPassActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSlideDown(ForgotPassActivity.this);
        finish();
    }

}