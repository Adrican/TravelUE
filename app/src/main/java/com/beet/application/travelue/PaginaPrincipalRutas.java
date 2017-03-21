package com.beet.application.travelue;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.github.clans.fab.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.util.ArrayList;

/**/


public class PaginaPrincipalRutas extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    TabRutasTotales content = null;
    DatabaseReference mDataBase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    RecyclerView recyclerView, recyclerBusqueda;

    private TextView emailNav;
    private TextView pruebita;

    private FirebaseAuth auth;

    private ProgressDialog progressDialog;
    private FloatingActionButton btnFloat, btnAtras, btnBuscar;

    private ImageView ivPerfil;


    static ArrayList<Route> lista_busquedas;


    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    };

    ArrayList<Route> lista_contactos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal_rutas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        auth = FirebaseAuth.getInstance();


        mDataBase = FirebaseDatabase.getInstance().getReference().child("usuarios");

        progressDialog = new ProgressDialog(this);



        //content.cargaContactos();
/**
 btnFloat = (FloatingActionButton) findViewById(R.id.fabCrearRuta);
 btnAtras = (FloatingActionButton) findViewById(R.id.fabVolverAtras);
 btnBuscar = (FloatingActionButton) findViewById(R.id.fabBuscarRuta);

 *//**


         //seleccionDeTab();

         btnFloat.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent intent = new Intent(PaginaPrincipalRutas.this, CreateRouteMap.class);
        startActivity(intent);
        }
        });



         btnAtras.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        lista_busquedas.clear();
        //content.cargaContactos();
        btnAtras.setVisibility(View.INVISIBLE);
        }
        });

         btnBuscar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent intent = new Intent(PaginaPrincipalRutas.this, SearchActivity.class);
        startActivity(intent);
        }
        });
         */
/**
 Usuario us = new Usuario("Paco","Lucas","123");
 lista_contactos.add(us);
 */
        /**
         if (auth.getCurrentUser() != null) {

         }
         */



/**
 recyclerView = (RecyclerView) findViewById(R.id.recyclerPrincipal);


 recyclerView.setHasFixedSize(true);
 layoutManager = new LinearLayoutManager(this);
 recyclerView.setLayoutManager(layoutManager);


 recyclerBusqueda = (RecyclerView) findViewById(R.id.recyclerPrincipal);
 recyclerBusqueda.setHasFixedSize(true);




 */

//
        //startSetupAccount();


        //Use of Fab



        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PaginaPrincipalRutas.this, CreateRouteMap.class);
                startActivity(intent);
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PaginaPrincipalRutas.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (TabRutasTotales.lista_busquedas != null && !TabRutasTotales.lista_busquedas.isEmpty()) {
                    TabRutasTotales.lista_busquedas.clear();
                }
                recargarLayout();


                //cargaContactos();


            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setFragment(new TabController());

        View header = navigationView.getHeaderView(0);
        pruebita = (TextView) header.findViewById(R.id.tvNombreUsuario);
        pruebita.setText(user.getDisplayName());
        emailNav = (TextView) header.findViewById(R.id.tvValoraciones);
        emailNav.setText(user.getEmail());

        ivPerfil = (ImageView) header.findViewById(R.id.imageView1);
        cogerImagen();
        ivPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);//CENTER_CROP

    }

    /**
     public void seleccionDeTab(){

     tablaPrincipal.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
    @Override
    public void onTabChanged(String arg0) {
    if (tablaPrincipal.getCurrentTab() == 1){
    btnFloat.setVisibility(View.VISIBLE);
    btnBuscar.setVisibility(View.INVISIBLE);
    btnAtras.setVisibility(View.INVISIBLE);
    } else if (tablaPrincipal.getCurrentTab()==0){
    btnFloat.setVisibility(View.INVISIBLE);
    btnBuscar.setVisibility(View.VISIBLE);
    }
    }
    });


     }
     */


    public void recargarLayout() {
        startActivity(new Intent(this, PaginaPrincipalRutas.class));

    }


    /**


     */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pagina_principal_rutas, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(PaginaPrincipalRutas.this, Profile.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_routes) {
            // Handle the camera action
            Intent intent = new Intent(PaginaPrincipalRutas.this, CreateRouteMap.class);
            this.startActivity(intent);
        } else if (id == R.id.nav_myroutes) {
            Intent intent = new Intent(PaginaPrincipalRutas.this, SearchActivity.class);
            this.startActivity(intent);

        } else if (id == R.id.nav_message) {

            Intent intentSurvey = new Intent(PaginaPrincipalRutas.this, PaginaPrincipalRutas.class);
            intentSurvey.putExtra("page", 2);
            startActivity(intentSurvey);

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(PaginaPrincipalRutas.this, Profile.class);
            this.startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.alerta_compartir) +" https://play.google.com/store/apps/details?id=com.beet.application.travelue&hl=es");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(PaginaPrincipalRutas.this, AboutUs.class);
            this.startActivity(intent);

        }
        else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intentSurvey = new Intent(PaginaPrincipalRutas.this, LoginActivity.class);
            startActivity(intentSurvey);

        }
        return true;
    }

    public void setFragment (Fragment fragment){

        if (fragment !=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_pagina_principal_rutas, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);



    }


    public void cogerImagen(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        new DownloadImageTask((ImageView) header.findViewById(R.id.imageView1))
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
        }
    }



}





