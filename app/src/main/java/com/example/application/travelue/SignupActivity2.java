package com.example.application.travelue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity2 extends AppCompatActivity {

    private EditText inputLive, inputNacionality, inputLanguages, inputEmail, inputSurname, inputPassword, inputName;
    private Button btnSignIn, btnSignUp, btnResetPassword;

    private ProgressBar progressBar;
    private ImageView imgProfile;
    private FirebaseAuth auth;
    private FloatingActionButton btnFloat;
    private static final int SELECT_FILE = 1;
    private static Usuario user;

    private StorageReference mStorage;
    private static final int  GALLERY_INTENT = 2;
    private ProgressDialog mProgresDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputLive = (EditText) findViewById(R.id.etLive);
        inputNacionality = (EditText) findViewById(R.id.etNacionality);
        inputLanguages = (EditText) findViewById(R.id.etLanguages);
        inputSurname = (EditText) findViewById(R.id.surname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        imgProfile = (ImageView) findViewById(R.id.ivNoPhoto);
        btnFloat = (FloatingActionButton) findViewById(R.id.fab);


        btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria(v);
            }
        });


        mStorage = FirebaseStorage.getInstance().getReference();
        mProgresDialog = new ProgressDialog(this);


/**
 btnResetPassword.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
}
});

 btnSignIn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
finish();
}
});
 */



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/**
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*


                //create user
                /**
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity2.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity2.this, "Only a last few things!", Toast.LENGTH_SHORT).show();
                */
                                progressBar.setVisibility(View.VISIBLE);

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                String residencia=inputLive.getText().toString();
                                String nacionalidad=inputNacionality.getText().toString();
                                String idiomas=inputLanguages.getText().toString();

                                user.setResidencia(residencia);
                                user.setNacionalidad(nacionalidad);
                                user.setIdiomas(idiomas);
                                insertarContacto(user);
/**
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity2.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                } else {
 */                                 //meterImagenEnFirebase();
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(new Intent(SignupActivity2.this, CreateRoute.class));
                                    finish();

                            }
                        });




    }

    public void abrirGaleria(View v){

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
        /**
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECT_FILE);
         */


    }


        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

                //Code for the progressBar Craeted
                mProgresDialog.setMessage("Uploading...");
                mProgresDialog.show();


                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    //Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    imgProfile.setImageURI(selectedImageUri);
                }
                StorageReference filepath = mStorage.child("Photos").child(selectedImageUri.getLastPathSegment());

                filepath.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(SignupActivity2.this, "Upload new Image file to Firebase done", Toast.LENGTH_LONG).show();
                        mProgresDialog.dismiss();

                    }
                });
            }
        }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }




/** FUNCIONA
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    //Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    imgProfile.setImageURI(selectedImageUri);

                }
            }
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    */

    private void insertarContacto(Usuario user)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("usuarios");
        String key = myRef.child("usuario").push().getKey();
        Map m=new HashMap<>();
        m.put(key,user);
        myRef.updateChildren(m);

    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    public static void setUser(Usuario usuario){
        user=usuario;
    }
}


