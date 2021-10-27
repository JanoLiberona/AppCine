package com.example.appcine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    TextView txtNameUser; //variable que usaremos para el nombre del usuario
    RecyclerView rcvComentarios;
    ListView lvTitles;
    String [] commentedTitles;
    ArrayList<String> listTitles;

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
        lvTitles = findViewById(R.id.tv_ListadoTitulos);
        listTitles = new ArrayList<String>();
        listTitles.add("El principe del RAP");
        listTitles.add("Space Jam");
        listTitles.add("MIB Hombres de Negro");
        listTitles.add("Malcolm in the middle");
        listTitles.add("Bob Esponja");
        listTitles.add("Los Padrinos mágicos");
        listTitles.add("Art attack!");
        listTitles.add("Mi pobre angelito");
        listTitles.add("Roger Rabbit");
        listTitles.add("El rey león");
        listTitles.add("El Chavo del 8");
        listTitles.add("Kablam!");
        listTitles.add("La vida moderna de Rocko");

        commentedTitles = new ArrayList<String>(listTitles).toArray(new String[0]); //ayuda del IDE. Parece funcionar

        for (int i = 0; i < commentedTitles.length; i++){
            listTitles.add(commentedTitles[i]);
        }

        ArrayAdapter<String> arrayAdapterComentarios = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listTitles);
        lvTitles.setAdapter(arrayAdapterComentarios);

        lvTitles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String titleName = commentedTitles[position];
                Intent intent = new Intent(view.getContext(),TitleActivity.class);
                intent.putExtra("nameCommentedTitles",titleName);
                startActivity(intent);
            }
        });



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