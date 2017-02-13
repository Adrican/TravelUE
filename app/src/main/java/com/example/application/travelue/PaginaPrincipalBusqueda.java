package com.example.application.travelue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class PaginaPrincipalBusqueda extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ProgressDialog progressDialog;
    RecyclerView recyclerView;
    private FirebaseAuth auth;
    RecyclerView.LayoutManager layoutManager;
    static ArrayList<Route> lista_contactos;

    RecyclerView.Adapter adapter=new RecyclerView.Adapter() {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal_busqueda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);


        TabHost tablaPrincipal;
        tablaPrincipal = (TabHost) findViewById(R.id.tabHost);
        tablaPrincipal.setup();

        TabHost.TabSpec tab1=tablaPrincipal.newTabSpec("Etiqueta1");
        TabHost.TabSpec tab2=tablaPrincipal.newTabSpec("Etiqueta2");
        TabHost.TabSpec tab3=tablaPrincipal.newTabSpec("Etiqueta3");

        tab1.setIndicator("Rutas");
        tab2.setIndicator("Mis Rutas");
        tab3.setIndicator("Historial");


        tab1.setContent(R.id.recycler_view2);
        tab2.setContent(R.id.recycler_view2);
        tab3.setContent(R.id.recycler_view2);

        tablaPrincipal.addTab(tab1);
        tablaPrincipal.addTab(tab2);
        tablaPrincipal.addTab(tab3);
        auth = FirebaseAuth.getInstance();



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        //Use of Fab
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //--
        //Set the routes in the cardView
        //--
        prueba();

    }
    public void prueba() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                recycler(lista_contactos);
                progressDialog.dismiss();

            }
        }, 3000);
    }

    private void recycler(ArrayList<Route>lista) {
        RecContactos rec = new RecContactos(this,lista);
        recyclerView.setAdapter(rec);
    }

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
        getMenuInflater().inflate(R.menu.pagina_principal_busqueda, menu);
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
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Set the contet of user to see in the card view
     * @param lista_contacto
     */
    public static void setArrayList(ArrayList<Route> lista_contacto){
        lista_contactos = lista_contacto;
    }
}

