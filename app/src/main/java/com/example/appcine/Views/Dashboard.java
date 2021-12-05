package com.example.appcine.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appcine.Adapters.MovieAdapter;
import com.example.appcine.Models.MovieModelClass;
import com.example.appcine.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    //TMDB API JSON link filtrado por populares: https://api.themoviedb.org/3/movie/popular?api_key=3b7f550a381e29852ffb145508b4bdb5&language=es-ES
    private String JSON_URL = "";

    List<MovieModelClass> movieList;
    RecyclerView recyclerView;
    ImageView header;
    Spinner spinner;
    List<String> listCategorias;
    ImageButton userAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setTitle("Dashboard");
        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        header = findViewById(R.id.header);
        spinner = findViewById(R.id.spinner);
        userAvatar = findViewById(R.id.ibtnUserAvatar);

        Glide.with(this).load(R.drawable.spider).into(header);

        //Spinner
        listCategorias = new ArrayList<String>();
        listCategorias.add("Popular");
        listCategorias.add("Más votados");
        listCategorias.add("Próximos");
        listCategorias.add("Para mí");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.my_selected_item, listCategorias);
        arrayAdapter.setDropDownViewResource(R.layout.my_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                GetData getData = new GetData();
                switch (position) {
                    case 0:
                        JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=3b7f550a381e29852ffb145508b4bdb5&language=es-ES";
                        getData.execute();
                        break;
                    case 1:
                        JSON_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=3b7f550a381e29852ffb145508b4bdb5&language=es-ES";
                        getData.execute();
                        break;
                    case 2:
                        JSON_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=3b7f550a381e29852ffb145508b4bdb5&language=es-ES";
                        getData.execute();
                        break;
                    case 3:
                        JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=3b7f550a381e29852ffb145508b4bdb5&language=es-ES";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, UserConfigsActivity.class);
                startActivity(intent);
            }
        });

        //Tarea Asíncrona
        //GetData getData = new GetData();
        //getData.execute();

        //No permite volver atrás
        onBackPressed();

    }

    @Override
    public void onBackPressed(){

    }

    //Clase para el manejo asíncrono de la API
    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    System.out.println(JSON_URL);
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while(data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return current;
        }


        //Manejo de los datos del JSON de la API
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                //La Api nos devuelve un un objeto que contiene un arreglo llamado "results"
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                //Eliminamos el array para que cada vez que seleccionamos un nuevo elemento en el spinner se vuelva a cargar la lista con el método add
                movieList.clear();
                //Dentro del array recorremos los objetos que hayan
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    MovieModelClass model = new MovieModelClass();
                    model.setId(jsonObject1.getString("id"));
                    model.setName(jsonObject1.getString("title"));
                    model.setImg(jsonObject1.getString("poster_path"));
                    model.setRdate(jsonObject1.getString("release_date"));
                    model.setOverview(jsonObject1.getString("overview"));
                    movieList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(movieList);
        }
    }

    private void PutDataIntoRecyclerView(List<MovieModelClass> movieList) {
        MovieAdapter adapter = new MovieAdapter(this, movieList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemview, int position) {
                String title = movieList.get(position).getName();
                String id = movieList.get(position).getId();
                String img = movieList.get(position).getImg();
                String rdate = movieList.get(position).getRdate();
                String overview = movieList.get(position).getOverview();
                Intent intent = new Intent(Dashboard.this, MovieDetailItem.class);
                intent.putExtra("title", title);
                intent.putExtra("id", id);
                intent.putExtra("poster_path", img);
                intent.putExtra("release_date", rdate);
                intent.putExtra("overview", overview);
                startActivity(intent);
            }
        });
    }

}