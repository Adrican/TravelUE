package com.beet.application.travelue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.Iterator;

public class Profile extends AppCompatActivity {



    private ProgressDialog progressDialog;

    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 2;

    private static Usuario usuario;

    private Uri mImageUri = null;
    private DatabaseReference mDataBase;





    private ImageView ivPerfil;
    private EditText inputLive, inputNacionality, inputLanguages;
    private TextView tvNombre, tvMail;
    private Toolbar toolbar;
    private Button btnActualizarDatos;
    private FloatingActionButton fbtnactualizaFoto;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String url = String.valueOf(user.getPhotoUrl());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(user.getDisplayName());


        ivPerfil = (ImageView) findViewById(R.id.ivProfilePicture);

        tvMail = (TextView) findViewById(R.id.tvProfileMail);
        tvMail.setText(user.getEmail());


        inputLive = (EditText) findViewById(R.id.etLive);
        inputLanguages = (EditText) findViewById(R.id.etLanguages);
        inputNacionality = (EditText) findViewById(R.id.etNacionality);



        fbtnactualizaFoto = (FloatingActionButton) findViewById(R.id.subirFoto);
        fbtnactualizaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria(v);
            }
        });

        mStorage = FirebaseStorage.getInstance().getReference();

        btnActualizarDatos = (Button) findViewById(R.id.btnActualizaPerfil);
        btnActualizarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //actualizarFoto();
                    //recorrerUsuarioActualizar();



            }
        });

        cogerImagen();


    }


    public void cogerImagen() {

        new DownloadImageTask((ImageView) findViewById(R.id.ivProfilePicture))
                .execute(String.valueOf(user.getPhotoUrl()));
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            bmImage.setScaleType(ImageView.ScaleType.CENTER_CROP);//CENTER_CROP
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, PaginaPrincipalRutas.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }


    public void recorrerUsuarioActualizar() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("usuarios");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference refNacionalidad = ref.child(user.getUid()).child("nacionalidad");



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Iterable i = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterador = i.iterator();
                while (iterador.hasNext()) {

                    DataSnapshot it = iterador.next();
                    final Usuario u = it.getValue(Usuario.class);
                    if (user.getEmail().equals(u.getEmail())) {
                        String nacional = inputNacionality.getText().toString();
                        refNacionalidad.setValue(nacional);

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("mira mi huevo", "");
            }
        });
    }






    public void actualizarFoto() {
        StorageReference filepath = mStorage.child("Photos").child(mImageUri.getLastPathSegment());
        filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String foto = downloadUrl.toString();




                FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();


                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(downloadUrl)
                        .setDisplayName(usuario.getDisplayName())
                        .build();

                usuario.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                Toast.makeText(Profile.this, "Upload new Image file to Firebase done", Toast.LENGTH_LONG).show();

                //mProgresDialog.dismiss();
                    /*
                    Intent intent = new Intent(SignupActivity2.this, PaginaPrincipalRutas.class);
                    startActivity(intent);
                    finish();
                    */
            }
        });
    }


    public void abrirGaleria(View v) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);



    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {



            mImageUri = data.getData();
            if (null != mImageUri) {
                // Get the path from the Uri
                String path = getPathFromURI(mImageUri);
                //Log.i(TAG, "Image Path : " + path);
                // Set the image in ImageView
                ivPerfil.setImageURI(mImageUri);
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


}

