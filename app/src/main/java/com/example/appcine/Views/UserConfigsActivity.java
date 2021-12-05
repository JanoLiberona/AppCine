package com.example.appcine.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.appcine.R;

public class UserConfigsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_configs);

        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        //Scroll when select a editText
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
}