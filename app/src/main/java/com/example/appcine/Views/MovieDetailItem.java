package com.example.appcine.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.appcine.Adapters.CommentAdapter;
import com.example.appcine.Database.AppDatabase;
import com.example.appcine.Database.Entities.CommentEntity;
import com.example.appcine.Helpers.Validate;
import com.example.appcine.Models.CommentModelClass;
import com.example.appcine.R;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailItem extends AppCompatActivity {

    TextView titleName, titleRdate, titleoOverview;
    ImageView titleImage;
    LottieAnimationView like;
    RecyclerView rvComment;
    EditText etContent;
    ImageButton ibtnAddComment;
    ArrayList<CommentModelClass> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_item);

        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        //Scroll when select a editText
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        titleName = findViewById(R.id.titleName);
        //TextView titleId = findViewById(R.id.titleId);
        titleRdate = findViewById(R.id.tvRdate);
        titleoOverview = findViewById(R.id.tvOverview);
        titleImage = findViewById(R.id.titleImage);
        like = findViewById(R.id.lfLike);
        rvComment = findViewById(R.id.rvComment);
        ibtnAddComment = findViewById(R.id.ibtnAddComment);
        etContent = findViewById(R.id.etMessage);

        //Animación botón like
        like.setOnClickListener(new View.OnClickListener() {
            boolean isAnimated = false;
            @Override
            public void onClick(View view) {
                if (!isAnimated) {
                    like.setSpeed(2f);
                    isAnimated = true;
                    like.playAnimation();
                } else {
                    like.setSpeed(-3F);
                    isAnimated = false;
                    like.playAnimation();
                }
            }
        });

        //Obtención de los datos de la actividad anterior
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String movieid = intent.getStringExtra("id");
        String img = intent.getStringExtra("poster_path");
        String rDate = intent.getStringExtra("release_date");
        String overview = intent.getStringExtra("overview");

        titleName.setText(title);
        //titleId.setText(id);
        titleRdate.setText(rDate);
        titleoOverview.setText(overview);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500"+img).into(titleImage);

        comments = CommentModelClass.listComments(MovieDetailItem.this);
        CommentAdapter adapter = new CommentAdapter(comments);
        rvComment.setAdapter(adapter);
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        ibtnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase database = AppDatabase.getInstance(MovieDetailItem.this);
                Long idUser = database.usersDAO().getAll().get(0).getId();
                String comment = etContent.getText().toString();
                String idmovie = movieid;
                String userName = database.usersDAO().getUser(idUser).get(0).getName();
                CommentEntity commentEntity = new CommentEntity(userName, comment, idmovie);
                database.commentDAO().insert(commentEntity);
                System.out.println(commentEntity.getComment());
                etContent.setText("");

            }
        });

    }


    private int validarDatos() {
        Validate validate = new Validate();
        int count = 0;
        String content = etContent.getText().toString();

        if (validate.checkNull(content)) {
            etContent.setError(null);
        } else {
            etContent.setError(getString(R.string.campo_nulo));
            count++;
        }
        return count;
    }
}