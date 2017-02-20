package com.example.application.travelue;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import com.github.clans.fab.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.TabHost;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**/


public class PaginaPrincipalRutas extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    TabRutasTotales content = null;
    DatabaseReference mDataBase;
    private FirebaseAuth mauth;


    RecyclerView recyclerView, recyclerBusqueda;

    private TextView pruebita;

    private ProgressDialog progressDialog;
    private FloatingActionButton btnFloat, btnAtras, btnBuscar;



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




        mDataBase = FirebaseDatabase.getInstance().getReference().child("usuarios");

        progressDialog = new ProgressDialog(this);


        mauth = FirebaseAuth.getInstance();
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
        mostrarNombre();


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
    public void mostrarNombre(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            String displayName = user.getDisplayName();
            Uri profileUri = user.getPhotoUrl();

            // If the above were null, iterate the provider data
            // and set with the first non null data
            for (UserInfo userInfo : user.getProviderData()) {
                if (displayName == null && userInfo.getDisplayName() != null) {
                    displayName = userInfo.getDisplayName();
                }
                if (profileUri == null && userInfo.getPhotoUrl() != null) {
                    profileUri = userInfo.getPhotoUrl();
                }
            }

            pruebita.setText(displayName);


        }

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            setFragment(new TabController());
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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





}
