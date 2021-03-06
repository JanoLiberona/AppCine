package com.example.appcine.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import com.example.appcine.Adapters.LoginAdapter;
import com.example.appcine.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    //Inicialización variables
    TabLayout tabLayout;
    ViewPager viewPager;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //Referencias a widgets
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        //Agregando las tabs al tablayout
        tabLayout.addTab(tabLayout.newTab().setText("Ingresar"));
        tabLayout.addTab(tabLayout.newTab().setText("Registrar"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //Añadiendo la funcionalidad del adapter
        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        //Al hacer swipe hacia un costado se cambia el fragment
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Al clickear el tab se cambia el fragment
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    adapter.notifyDataSetChanged();
                }
                if (tab.getPosition() == 1) {
                    adapter.notifyDataSetChanged();
                }
                if (tab.getPosition() == 2) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        tabLayout.setTranslationY(300);
        tabLayout.setAlpha(0);
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();

    }

}