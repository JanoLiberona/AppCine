package com.example.appcine.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.appcine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AvatarSelectionActivity extends AppCompatActivity {

    ImageView userImage;
    ImageButton btnSelectImage;
    Button btnNext;
    TextView userName;
    static int PReqCode = 1;
    static int REQUESTCODE = 1;
    Uri pickedImgUri;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    FirebaseUser currentUser;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_selection);

        //Edge to edge screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        userImage = findViewById(R.id.ivSelectUserImage);
        btnSelectImage = findViewById(R.id.btnAddUserImage);
        btnNext = findViewById(R.id.btnNext);
        userName = findViewById(R.id.tvUserName);
        progressBar = findViewById(R.id.pbUploadingImage);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        userName.setText(" "+user);

        btnNext.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);




        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 21) {
                    checkAndRequestforPermission();
                } else {
                    openGallery();
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });


        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 21) {
                    checkAndRequestforPermission();
                } else {
                    openGallery();
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameIntent = userName.getText().toString();
                Intent intent = new Intent(AvatarSelectionActivity.this, PreferencesActivity.class);
                intent.putExtra("userName", userNameIntent);
                startActivity(intent);
                Animatoo.animateSwipeLeft(AvatarSelectionActivity.this);
            }
        });

        //No permite volver atrás
        onBackPressed();

    }


    //Método para preguntar y aceptar los permisos para la galería
    private void checkAndRequestforPermission() {
        if (ContextCompat.checkSelfPermission(AvatarSelectionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(AvatarSelectionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showMessage(getString(R.string.acceptPermission));
            } else {
                ActivityCompat.requestPermissions(AvatarSelectionActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        } else {
            openGallery();
        }
    }


    //Método para abrir la galería
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUESTCODE);
    }


    //método que obtiene desde la galería la foto y la setea en el widget de la imagen de usuario
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null) {
            //El usuario ha seleccionado una foto de manera correcta
            //Guardamos la foto
            pickedImgUri = data.getData();
            userImage.setImageURI(pickedImgUri);

            uploadImageToFirebase(userName.getText().toString(),pickedImgUri, mAuth.getCurrentUser());
            progressBar.setVisibility(View.VISIBLE);
            showMessage(getString(R.string.uploadingImage));
        }
    }


    //Método para subir la imagen a la cuenta de usuario en firebase
    private void uploadImageToFirebase(final String name, Uri pickedImgUri, final FirebaseUser currentUser) {
        StorageReference sReference = storageReference.child("users/"+currentUser.getUid()+"/profile.jpg");
        sReference.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();

                        Glide.with(AvatarSelectionActivity.this).load(uri).into(userImage);


                        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                showMessage(getString(R.string.imageUpdated));
                                btnNext.setVisibility(View.VISIBLE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showMessage(getString(R.string.uploadingImageError));
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showMessage(getString(R.string.imageUpdatedFailure));
            }
        });
    }


    //Para mostrar mensaje toast
    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed(){

    }
}