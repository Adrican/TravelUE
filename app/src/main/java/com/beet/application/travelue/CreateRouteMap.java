package com.beet.application.travelue;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateRouteMap extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener {


    private GoogleMap mMap;
    private Button btnFindPath, mbtnCreateRoute, btnBack;
    private ImageView imgNoCar, imgNoFumar, imgNoComer, imgNoPersonas, imgSiFumar, imgSiComer, imgSiPersonas;
    private AutoCompleteTextView etOrigin;
    private AutoCompleteTextView etDestination;
    private EditText etCantidadPasajeros;
    private EditText modeloCoche;
    private TextView seguro;
    private TextInputLayout modelCoche;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private ProgressBar mpBarRoute;
    private FloatingActionButton btnFloat;


    private static final int  GALLERY_INTENT = 2;


    private StorageReference mStorage;
    private ProgressDialog mProgresDialog;


    private Spinner spConducPasaj, spCarInsurance;
    private TimePicker tpHora;
    private EditText metTime, metDate, metDate2;
    int hour_x;
    int minute_x;
    private ArrayList<String> prueba = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        seguro = (TextView) findViewById(R.id.tvInsurance);

        mStorage = FirebaseStorage.getInstance().getReference();
        mProgresDialog = new ProgressDialog(this);
        //imgNoCar = (ImageView) findViewById(R.id.ivNoPhotoxx);
        etOrigin = (AutoCompleteTextView) findViewById(R.id.etOrigen);
        etOrigin.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item));

        etDestination = (AutoCompleteTextView) findViewById(R.id.etDestino);
        etDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDestination.setText("");
            }
        });

        //etOrigin.setText("Introduce the start of the route");
        //etDestination.setText("Introduce where are you going");
        etDestination.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item));

        mbtnCreateRoute = (Button) findViewById(R.id.btnCreateRoute);
        btnBack = (Button) findViewById(R.id.btnBack);
        mpBarRoute = (ProgressBar) findViewById(R.id.pBarRoute);

        imgNoFumar = (ImageView) findViewById(R.id.imgNoFumar);
        imgNoComer = (ImageView) findViewById(R.id.imgNoComer);
        imgNoPersonas = (ImageView) findViewById(R.id.imgNoPersonas);

        imgSiFumar = (ImageView) findViewById(R.id.imgSiFumar);
        imgSiComer = (ImageView) findViewById(R.id.imgSiComer);
        imgSiPersonas = (ImageView) findViewById(R.id.imgSiPersonas);

        modeloCoche = (EditText) findViewById(R.id.etCar);
        modelCoche = (TextInputLayout) findViewById(R.id.modelcoche);
        etCantidadPasajeros = (EditText) findViewById(R.id.etCantidadPasajeros);
        etCantidadPasajeros.setText("1");

        btnFloat = (FloatingActionButton) findViewById(R.id.mapearRuta);


        btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });



        cargarVistas();
        sacarHora();
        sacarFechaDesde();
        sacarFechaHasta();
        comportamientoIconos();

        mbtnCreateRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpBarRoute.setVisibility(View.VISIBLE);
                insertFirebase();
                Intent intent = new Intent(CreateRouteMap.this, PaginaPrincipalRutas.class);
                startActivity(intent);
            }
        });


    }


    public void comportamientoIconos(){
        imgSiFumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSiFumar.setVisibility(View.INVISIBLE);
                imgNoFumar.setVisibility(View.VISIBLE);
            }
        });
        imgNoFumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSiFumar.setVisibility(View.VISIBLE);
                imgNoFumar.setVisibility(View.INVISIBLE);
            }
        });
        imgSiComer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSiComer.setVisibility(View.INVISIBLE);
                imgNoComer.setVisibility(View.VISIBLE);
            }
        });
        imgNoComer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSiComer.setVisibility(View.VISIBLE);
                imgNoComer.setVisibility(View.INVISIBLE);
            }
        });
        imgSiPersonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void sendRequest() {
        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(this, getString(R.string.alerta_origen), Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, getString(R.string.alerta_destino), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
            btnFloat.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng uem = new LatLng(40.372607, -3.918427);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uem, 16)); // newLatLngZoom hara que se acerque
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.rabanopng))
                .title("Universidad Europea de Madrid")
                .position(uem)));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, getString(R.string.alerta_esperar),
                getString(R.string.alerta_encontrar), true);

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
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
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

    public void cargarVistas() {


        final Spinner spConducPasaj = (Spinner) findViewById(R.id.spEstado);


        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource(this, R.array.options, R.layout.spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spConducPasaj.setAdapter(spinner_adapter);



        final Spinner spCarInsurance = (Spinner) findViewById(R.id.spCarInsurance);


        ArrayAdapter saCarInsurance = ArrayAdapter.createFromResource(this, R.array.insurance, R.layout.spinner_item);
        saCarInsurance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCarInsurance.setAdapter(saCarInsurance);


        spConducPasaj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pasajero = spConducPasaj.getItemAtPosition(position).toString();
                if (pasajero.equals("Passenger")) {
                    modeloCoche.setVisibility(View.INVISIBLE);
                    modelCoche.setVisibility(View.INVISIBLE);
                    spCarInsurance.setVisibility(View.INVISIBLE);
                    seguro.setVisibility(View.INVISIBLE);



                }else if(pasajero.equals("Driver")) {
                    modeloCoche.setVisibility(View.VISIBLE);
                    modelCoche.setVisibility(View.VISIBLE);
                    spCarInsurance.setVisibility(View.VISIBLE);
                    seguro.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner spUniversidades = (Spinner) findViewById(R.id.spuniversities);


        ArrayAdapter saUniversidades = ArrayAdapter.createFromResource(this, R.array.universities, R.layout.spinner_item);
        saUniversidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUniversidades.setAdapter(saUniversidades);


        spUniversidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String universidad = spUniversidades.getItemAtPosition(position).toString();
                etDestination.setText(universidad);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }









    public void sacarHora() {
        final EditText metTime = (EditText) findViewById(R.id.etTime);
        metTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateRouteMap.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        metTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.alerta_hora));
                mTimePicker.show();


            }
        });
    }





    public void sacarFechaDesde(){
        final EditText metDate = (EditText) findViewById(R.id.etDate);
        metDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(CreateRouteMap.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        metDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle(getString(R.string.alerta_fecha));
                mDatePicker.show();
            }
        });

    }

    public void sacarFechaHasta(){
        final EditText metDate2 = (EditText) findViewById(R.id.etDate2);
        metDate2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_WEEK);


                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(CreateRouteMap.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        metDate2.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle(getString(R.string.alerta_fecha));
                mDatePicker.show();
            }
        });

    }

/**
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
         *


    }


    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            //Code for the progressBar Craeted
            /**
             mProgresDialog.setMessage("Uploading...");
             mProgresDialog.show();
             */

/**
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                // Get the path from the Uri
                String path = getPathFromURI(selectedImageUri);
                //Log.i(TAG, "Image Path : " + path);
                // Set the image in ImageView
                imgNoCar.setImageURI(selectedImageUri);
            }
            StorageReference filepath = mStorage.child("CarPhotos").child(selectedImageUri.getLastPathSegment());

            filepath.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CreateRouteMap.this, "Upload new Image file to Firebase done", Toast.LENGTH_LONG).show();
                    mProgresDialog.dismiss();

                }
            });
        }
    }
*/
/**
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




    /**
     * Method for inserting the Route
     */
    private void insertFirebase(){
        Route mR = createRoute();
        if(!mR.equals(null)) {
            try {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("rutas");
                String key = myRef.child("ruta").push().getKey();
                Map m = new HashMap<>();
                mR.setReferenceKey(key);
                m.put(key,mR);
                myRef.updateChildren(m);
                Toast.makeText(CreateRouteMap.this, getString(R.string.alerta_nuevaruta), Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(CreateRouteMap.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Get all the informatiom from the layout an creae and object
     * @return new Route()
     */
    private Route createRoute(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String distance,duration,enAdress,startAddress,typeOfUser,carModel,typeOfInsurance,hour,startDay,finisDay, emailUser, nombreUser, fotoPerfil;
        Duration dura;
        Distance dist;
        //Route distancia = ;
        boolean allowEating, allowSmoking;
        int numberOfPasangers;


        //distancia.getDistance().text = ((TextView)findViewById(R.id.tvDistance)).getText().toString();
        duration = ((TextView)findViewById(R.id.tvDuration)).getText().toString();
        distance =((TextView)findViewById(R.id.tvDistance)).getText().toString();

        //Get the content
        enAdress = ((TextView)findViewById(R.id.etDestino)).getText().toString();
        startAddress = ((TextView) findViewById(R.id.etOrigen)).getText().toString();
        typeOfUser = ((Spinner)findViewById(R.id.spEstado)).getSelectedItem().toString();//---Could be a problem
        carModel = ((TextView) findViewById(R.id.etCar)).getText().toString();
        typeOfInsurance = ((Spinner) findViewById(R.id.spCarInsurance)).getSelectedItem().toString();
        hour = ((TextView) findViewById(R.id.etTime)).getText().toString();
        startDay = ((TextView) findViewById(R.id.etDate)).getText().toString();
        finisDay = ((TextView) findViewById(R.id.etDate2)).getText().toString();
        nombreUser = user.getDisplayName();
        emailUser = user.getEmail();
        fotoPerfil = String.valueOf(user.getPhotoUrl());


        allowEating = isVisible(imgSiComer);
        allowSmoking = isVisible(imgSiFumar);

        numberOfPasangers = Integer.parseInt(String.valueOf(etCantidadPasajeros.getText()));

        try {
            return new Route(distance, duration, enAdress, startAddress, typeOfUser, carModel, typeOfInsurance, hour, startDay, finisDay, allowEating, allowSmoking, numberOfPasangers, emailUser, nombreUser, fotoPerfil);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Now if the imagen is VISIBLE
     * @param img
     * @return
     */
    private boolean isVisible(ImageView img){
        if(img.getVisibility() == View.INVISIBLE){
            return false;
        }else{
            return true;
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


}
