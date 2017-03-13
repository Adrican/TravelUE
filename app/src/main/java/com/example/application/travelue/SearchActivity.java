

package com.example.application.travelue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchActivity extends AppCompatActivity {
    ArrayList<Route> lista_contactos;

    //TextView txt_Origen;
    //TextView txt_Destino;
    private AutoCompleteTextView etOrigin;
    private AutoCompleteTextView etDestination;
    TextView txt_Time;
    TextView txt_Date;
    TextView txt_Date_2;
    TextView txt_Distance;
    TextView txt_Duration;
    Button btn_Find_Path;
    Button btn_Back;
    Button btn_Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile");
        componetsCharge();
    }

    //--
    //Componets to be used
    //--

    /**
     * Charge all the tools that will be used
     */
    public void componetsCharge() {
        etOrigin = (AutoCompleteTextView) findViewById(R.id.etOrigen);
        etOrigin.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item));

        etDestination = (AutoCompleteTextView) findViewById(R.id.etDestino);
        etDestination.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item));
        /**
        txt_Time = (TextView) findViewById(R.id.etTime);
        txt_Date = (TextView) findViewById(R.id.etDate);
        txt_Date_2 = (TextView) findViewById(R.id.etDate2);
        txt_Duration = (TextView) findViewById(R.id.tvDuration);
        txt_Distance = (TextView) findViewById(R.id.tvDistance);
        //btn_Find_Path = (Button) findViewById(R.id.btnFindPath);
         */
        //btn_Back = (Button) findViewById(R.id.btnBack);
        btn_Search = (Button) findViewById(R.id.btnSearchRoute);

        //btnFindPathListner();
        //btnBackListener();
        btnSearchListener();
    }

    //--
    //Btn Listeners
    //--

    /**
     * Listener to make when btn_Find_Path is clicked
     */
    public void btnFindPathListner() {
        /**
         btn_Find_Path.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {

        }
        });
         */
    }

    /**
     * Listener to get to the other layaout
     */
    /**
    public void btnBackListener() {
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Change to layout PaguinaPrincipalRutas
                Intent intent = new Intent(SearchActivity.this, PaginaPrincipalRutas.class);
                startActivity(intent);
            }
        });
    }

    /**
     * listener to get the search of the btn_Search
     */

    public void btnSearchListener() {
        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Set the user in the
                TabRutasTotales.setArrayList(ChargeRoutes(etOrigin.getText().toString(), etDestination.getText().toString()));

                //Change to layout PaguinaPrincipalRutas
                Intent intent = new Intent(SearchActivity.this, PaginaPrincipalRutas.class);
                startActivity(intent);
            }
        });
    }

    //--
    //Method to make the things
    //--

    /**
     * Method to search the Route
     *
     * @param origen
     * @param destiny
     * @return
     */
    public ArrayList ChargeRoutes(final String origen, final String destiny) {
        lista_contactos = new ArrayList<>();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        lista_contactos = new ArrayList();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("rutas");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lista_contactos.clear();
                Iterable i = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterador = i.iterator();
                while (iterador.hasNext()) {
                    Route route = iterador.next().getValue(Route.class);
                    if(route.getStartAddress().equals(origen) && route.getEndAddress().equals(destiny)) {
                        lista_contactos.add(route);
                    }
                    else if(!route.getStartAddress().equals(origen) && !route.getEndAddress().equals(destiny)){
                        Toast.makeText(getApplicationContext(), "No routes found ;(. And if you publish it? :)", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Problem Charging the routes", Toast.LENGTH_SHORT).show();
                /*
                Route a = new Route("","","","","","","","", false, false, 0);
                lista_contactos.add(a);
                */
            }
        });
        return lista_contactos;
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
}