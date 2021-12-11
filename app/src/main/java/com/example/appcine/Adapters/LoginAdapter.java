package com.example.appcine.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.appcine.Views.Fragments.LoginTabFragment;
import com.example.appcine.Views.Fragments.RegisterTabFragment;

//clase para cambiar entre tabs
public class LoginAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;

    //Constructor
    public LoginAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs= totalTabs;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    //Para hacer mostrar los fragmentos en la pantalla
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LoginTabFragment loginTabFragment = new LoginTabFragment();
                return loginTabFragment;
            case 1:
                RegisterTabFragment registerTabFragment = new RegisterTabFragment();
                return registerTabFragment;
            default:
                return null;
        }
    }
}
