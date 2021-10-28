package com.example.appcine;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterTabFragment extends Fragment {

    private Button btnRegister;
    private TextInputLayout tilUser, tilPass, tilRpass, tilBday;
    private ImageView iconEmail, iconPass, iconRpass, iconBday;
    private CheckBox chkAccept;
    private Button getBtnRegister;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.register_tab_fragment, container, false);

        tilUser = root.findViewById(R.id.til_correo);
        tilPass = root.findViewById(R.id.til_pass);
        tilRpass = root.findViewById(R.id.til_RContraseña);
        tilBday = root.findViewById(R.id.til_FdNacimiento);
        chkAccept = root.findViewById(R.id.chkTerminos);
        btnRegister = root.findViewById(R.id.btnRegistrar);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PreferencesActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}