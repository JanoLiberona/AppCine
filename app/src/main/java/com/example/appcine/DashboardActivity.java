package com.example.appcine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    TextView txtNameUser; //variable que usaremos para el nombre del usuario
    String [] comentarios;
    ListView lvComentarios;
    RecyclerView rcvComentarios;
    ArrayList<String> listComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //OBTENEMOS VARIABLE DE NUETRA ACTIVIDAD ANTERIOR
        String nameUser = getIntent().getStringExtra("nameUser");
        //ASIGNAREMOS DICHA VARIABLE A EL TextView generado
        txtNameUser = (TextView) findViewById(R.id.nameUser);
        txtNameUser.setText(nameUser);

        //_________ List view ___________cómo lo hizo el profe,03:02 clase del 23/09/2021 //
        lvComentarios = findViewById(R.id.tv_ListadoComentarios);
        listComentarios = new ArrayList<String>();
        listComentarios.add("Un bodrio");
        listComentarios.add("Película culiá");
        listComentarios.add("Mejor que me ha pasado");
        listComentarios.add("Malísima");
        listComentarios.add("Mala mala");
        listComentarios.add("Original");
        listComentarios.add("Rasca");
        listComentarios.add("Repulsivamente... buena!");
        listComentarios.add("Demasiado buena para ser verdad");
        listComentarios.add("Despreciable");
        listComentarios.add("Buenísima");
        listComentarios.add("Caca");
        listComentarios.add("Sin carne");

        ArrayAdapter<String> arrayAdapterComentarios = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listComentarios);
        lvComentarios.setAdapter(arrayAdapterComentarios);



        /*
        //________ Recycler view _________ cómo lo hizo el profe, segundo intento 02:50 clase del 23/09/2021 no le funcionó y se fue a lv //
        rcvComentarios = (RecyclerView) findViewById(R.id.rcvComentarios); //traemos de la vista
        listComentarios = new ArrayList<String>();
        listComentarios.add("Un bodrio");
        listComentarios.add("Película culiá");
        listComentarios.add("Mejor que me ha pasado");
        listComentarios.add("Malísima");
        listComentarios.add("Mala mala");
        listComentarios.add("Original");
        listComentarios.add("Rasca");
        listComentarios.add("Repulsivamente... buena!");
        listComentarios.add("Demasiado buena para ser verdad");
        listComentarios.add("Despreciable");
        listComentarios.add("Buenísima");
        listComentarios.add("Caca");
        listComentarios.add("Sin carne");

        ArrayAdapter<String> arrayAdapterComentarios = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1.listComentarios);
        rcvComentarios.setAdapter(arrayAdapterComentarios);
         */





    }
}