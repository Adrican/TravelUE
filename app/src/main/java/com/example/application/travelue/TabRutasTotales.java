package com.example.application.travelue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Adri on 16/02/2017.
 */
public class TabRutasTotales extends Fragment {
    RecyclerView recyclerView, recyclerBusqueda;
    View view;


    private ProgressDialog progressDialog;
    static ArrayList<Route> lista_busquedas;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_tab_rutas_totales,container,false);


        progressDialog = new ProgressDialog(this.getContext());


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerPrincipal);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        cargaContactos();


        if (lista_busquedas != null && !lista_busquedas.isEmpty()) {
            prueba();
        }










        return view;
    }



    public void prueba() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                recyclerBusqueda(lista_busquedas);
                //btnAtras.setVisibility(View.VISIBLE);
                progressDialog.dismiss();

            }
        }, 3000);

    }




    private void recyclerBusqueda(ArrayList<Route> lista) {
        RecContactos rec = new RecContactos(this.getContext(), lista);
        recyclerView.setAdapter(rec);
    }
    private void recycler(ArrayList<Route> lista) {
        RecContactos rec = new RecContactos(this.getContext(), lista);
        recyclerView.setAdapter(rec);
    }



    protected void cargaContactos() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("rutas");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lista_contactos.clear();
                Iterable i = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterador = i.iterator();
                while (iterador.hasNext()) {

                    Route r = iterador.next().getValue(Route.class);
                    lista_contactos.add(r);

                }
                //cargarListView(lista_contactos);
                recycler(lista_contactos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("mira mi huevo", "");
            }
        });

    }

    public static void setArrayList(ArrayList<Route> lista_busqueda) {
        lista_busquedas = lista_busqueda;
    }
}
