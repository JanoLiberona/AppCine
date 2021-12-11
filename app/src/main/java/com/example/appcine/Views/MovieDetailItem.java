package com.example.appcine.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.appcine.Adapters.CommentAdapter;
import com.example.appcine.Database.AppDatabase;
import com.example.appcine.Database.Entities.LikedMovieEntity;
import com.example.appcine.Helpers.Validate;
import com.example.appcine.Models.CommentModel;
import com.example.appcine.Models.MovieModel;
import com.example.appcine.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MovieDetailItem extends AppCompatActivity {

    TextView titleName, titleRdate, titleoOverview, likesCount;
    ImageView titleImage, currentUserImage, imgCurrentUSer;
    LottieAnimationView like;
    RecyclerView rvComment;
    EditText etContent;
    ImageButton ibtnAddComment;
    List<CommentModel> listComment;
    CommentAdapter commentAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore fdatabase;
    MovieModel movieModel;
    String userProfileImageURL;
    static String COMMENT_KEY = "Comment" ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_item);

        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);


        //Scroll when select a editText
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        titleName = findViewById(R.id.titleName);
        titleRdate = findViewById(R.id.tvRdate);
        titleoOverview = findViewById(R.id.tvOverview);
        titleImage = findViewById(R.id.titleImage);
        like = findViewById(R.id.lfLike);
        rvComment = findViewById(R.id.rvComment);
        ibtnAddComment = findViewById(R.id.ibtnAddComment);
        etContent = findViewById(R.id.etMessage);
        currentUserImage = findViewById(R.id.ivCurrentImageUSer);
        imgCurrentUSer = findViewById((R.id.ivImgCurrentUSer));
        likesCount = findViewById(R.id.tcLikesCount);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        fdatabase = FirebaseFirestore.getInstance();


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

        movieModel = new MovieModel(movieid, title, img, rDate, overview);
        uploadMovieDataToFirebase(movieModel);


        Glide.with(this).load(firebaseUser.getPhotoUrl()).into(imgCurrentUSer);



        //Obteniendo los datos de la película actual para luego guardarlos en la base de datos
        AppDatabase database = AppDatabase.getInstance(MovieDetailItem.this);
        LikedMovieEntity likedMovieEntity = new LikedMovieEntity(movieid, title, img, rDate, overview);

        boolean isLiked;
        if (database.likedMovieDAO().getLikedMovie(movieid) == null) {
            isLiked = false;
        } else {
            isLiked = true;
            like.playAnimation();
        }

        numLikes(likesCount, movieid);

        //Animación botón like y guardado o elimado de datos en la base de datos
        like.setOnClickListener(new View.OnClickListener() {
            boolean isLikedYet = isLiked;
            @Override
            public void onClick(View view) {
                if (isLikedYet == false) {
                    isLikedYet = true;
                    like.setSpeed(2f);
                    like.playAnimation();
                    database.likedMovieDAO().insert(likedMovieEntity);
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(movieid).child(firebaseUser.getUid()).setValue(true);
                } else {
                    isLikedYet = false;
                    like.setSpeed(-3F);
                    like.playAnimation();
                    database.likedMovieDAO().delete(likedMovieEntity);
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(movieid).child(firebaseUser.getUid()).removeValue();
                }
            }
        });


        ibtnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference commentReference = firebaseDatabase.getInstance().getReference("Comments").child(movieModel.getId()).child("Movies").push();
                String commentContent = etContent.getText().toString();
                String idUser = firebaseUser.getUid();
                String userName = firebaseUser.getDisplayName();
                String userImg = firebaseUser.getPhotoUrl().toString();

                CommentModel commentModel = new CommentModel(commentContent, idUser, userImg, userName);

                if (commentContent.equals("")) {
                    showMessage("Debe agregar un comentario");
                } else {
                    commentReference.setValue(commentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            showMessage("Comentario agregado");
                            etContent.setText("");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showMessage("Error al añadir agregar el comentario: "+e.getMessage());
                        }
                    });
                }



            }
        });

        iniRvComment();

    }

    private void iniRvComment() {
        rvComment.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = firebaseDatabase.getInstance().getReference("Comments").child(movieModel.getId()).child("Movies");

        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComment = new ArrayList<>();
                for (DataSnapshot snap:dataSnapshot.getChildren()) {
                    CommentModel commentModel = snap.getValue(CommentModel.class);
                    listComment.add(commentModel) ;
                }
                commentAdapter = new CommentAdapter(getApplicationContext(), listComment);
                rvComment.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    private void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private String timestampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;
    }

    private void uploadMovieDataToFirebase(MovieModel movieModel) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Movies").child(movieModel.getId());

        myRef.setValue(movieModel);

    }

    private void numLikes(TextView likes, String movieId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(movieId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                likes.setText(snapshot.getChildrenCount()+" me gusta");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateZoom(MovieDetailItem.this);
    }




}