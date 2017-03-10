package com.example.application.travelue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactoSeleccionado extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener  {

    private GoogleMap mMap;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();


    private static String nombre = "";
    private static String coche = "";
    private static String origen = "";
    private static String destino = "";
    private static String foto = "";
    private static String idiomas = "";
    private static String nacionalidad = "";
    private static String seguro = "";
    private static String mail = "";
    private FloatingActionButton mensajear;
    private TextView tvNombre, tvCoche, tvOrigen, tvDestino, tvNacionalidad, tvSeguro, tvNacional, tvIdioma;
    private ImageView ivFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto_seleccionado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(nombre);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*//
        tvNombre = (TextView) findViewById(R.id.tvNombreContacto);
        tvNombre.setText(nombre);
        */
        tvIdioma = (TextView) findViewById(R.id.tvIdiomasContacto);

        tvCoche = (TextView) findViewById(R.id.tvCocheContacto);
        tvCoche.setText(coche);

        tvOrigen = (TextView) findViewById(R.id.tvOrigenRuta);
        tvOrigen.setText(origen);
        tvDestino = (TextView) findViewById(R.id.tvDestinoRuta);
        tvDestino.setText(destino);


        tvNacional = (TextView) findViewById(R.id.tvNacionalidadContacto);


        tvSeguro = (TextView) findViewById(R.id.tvSeguro);
        tvSeguro.setText(seguro);
        sacarDatosUsuario();

        sendRequest();

        ivFoto = (ImageView) findViewById(R.id.ivFotoContacto);
        cogerImagen();
        ivFoto.setScaleType(ImageView.ScaleType.CENTER_CROP);//CENTER_CROP

        mensajear = (FloatingActionButton) findViewById(R.id.fab);

        mensajear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent homeIntent = new Intent(ContactoSeleccionado.this, ChatInterfaz.class);
                startActivity(homeIntent);
                */
            }
        });

    }



    public static void setName(String name) {
        nombre = name;
    }
    public static void setCar(String car) {
        coche = car;
    }
    public static void setOrigin(String origin) {
        origen = origin;
    }
    public static void setDestination(String destination) {
        destino = destination;
    }
    public static void setPicture(String picture) {
        foto = picture;
    }
    public static void setLanguages(String languages) {
        idiomas = languages;
    }

    public static String setEmail(String email) {
        mail = email;
        return email;
    }

    public static void setInsurance(String insurance) {
        seguro = insurance;
    }

    public void cogerImagen(){
        new DownloadImageTask((ImageView) findViewById(R.id.ivFotoContacto))
                .execute(foto);
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

    private void sendRequest() {
        String origin = origen;
        String destination = destino;
        /*
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }
        */
        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng uem = new LatLng(40.372607, -3.918427);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uem, 16)); // newLatLngZoom hara que se acerque

    }


    @Override
    public void onDirectionFinderStart() {


        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {

        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 10));

            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance);


            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.rabanopng))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.rabanopng))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.rgb(164,46,54)).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }


    /**
     * Con este metodo recorreremos los usuarios para sacar los datos de dentro.
     * @param
     * @return
     */
    public void sacarDatosUsuario(){
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference ref = database.getReference("usuarios");
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final String email = mail;




    ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {


            Iterable i = dataSnapshot.getChildren();
            Iterator<DataSnapshot> iterador = i.iterator();
            while (iterador.hasNext()) {

                DataSnapshot it = iterador.next();
                final Usuario u = it.getValue(Usuario.class);
                if (email.equals(u.getEmail())) {
                    tvNacional.setText(u.getNacionalidad());
                    tvIdioma.setText(u.getIdiomas());

                } else {

                }


            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.v("mira mi huevo", "");
        }
    });
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
