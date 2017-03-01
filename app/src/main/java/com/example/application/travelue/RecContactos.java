

package com.example.application.travelue;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


/**
     * Created by Eduardo on 24/11/2016.
 */
public class RecContactos extends RecyclerView.Adapter<RecContactos.ContactoViewHolder>  {

    private ArrayList<Route> items;
    public Context context;

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        public CardView cv;
        public TextView nombre;
        public TextView estado;
        public TextView origen;
        public TextView destino;
        public TextView numberPassengers;
        public TextView hours;
        public TextView calendar;
        public ImageView imagen;
        public ImageView estadoConductor;
        public ImageView imageSmoke;
        public ImageView imageEat;


        public ContactoViewHolder(View itemView) {
            super(itemView);


            cv = (CardView) itemView.findViewById(R.id.card_view);


            nombre = (TextView) itemView.findViewById(R.id.tvNombre);
            numberPassengers = (TextView) itemView.findViewById(R.id.tvNumberPassengers);
            estado = (TextView) itemView.findViewById(R.id.tvEstado);
            origen = (TextView) itemView.findViewById(R.id.tvOrigen);
            destino = (TextView) itemView.findViewById(R.id.tvDestino);
            hours = (TextView) itemView.findViewById(R.id.tvHoraSalida);
            calendar = (TextView) itemView.findViewById(R.id.tvFechas);
            imagen = (ImageView) itemView.findViewById(R.id.item_image);

            estadoConductor = (ImageView) itemView.findViewById(R.id.ivEstado);
            // new DownloadImageTask((ImageView) itemView.findViewById(R.id.item_image)).execute(String.valueOf(user.getPhotoUrl()));
            //imagen = (ImageView) itemView.findViewById(R.id.item_image);
            //imagen2 = (ImageView) itemView.findViewById(R.id.item_image2);
            ///Creamos
            imageSmoke = (ImageView) itemView.findViewById(R.id.ivSiFumar);
            imageEat = (ImageView) itemView.findViewById(R.id.ivSiComer);
            imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);//CENTER_CROP



        }
    }

        // show The Image in a ImageView


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
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }



    public RecContactos(Context context,ArrayList<Route> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public ContactoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        return new ContactoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ContactoViewHolder holder, final int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        if (items.get(position).getFotoPerfil()!=null) {

        new DownloadImageTask(holder.imagen).execute(items.get(position).getFotoPerfil());
        }



        holder.calendar.setText(items.get(position).getStartDay() + " - " +items.get(position).getFinisDay());
        holder.hours.setText(items.get(position).getHour());

        holder.nombre.setText(items.get(position).getNombreUser());
        holder.estado.setText(items.get(position).getTypeOfUser());
        holder.origen.setText(items.get(position).getStartAddress());
        holder.destino.setText(items.get(position).getEndAddress());
        holder.numberPassengers.setText(String.valueOf(items.get(position).getNumberOfPasangers()));



        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("rutas");
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
/**
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Iterable i = dataSnapshot.getChildren();
                        Iterator<DataSnapshot> iterador = i.iterator();
                        while (iterador.hasNext()) {

                            Route r = iterador.next().getValue(Route.class);
                */
                            if (user.getEmail().equals(items.get(position).getEmailUser())){
                                new AlertDialog.Builder(context)
                                        .setTitle("Delete entry")
                                        .setMessage("Are you sure you want to delete this entry?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                holder.cv.setVisibility(View.INVISIBLE);
                                                borraRuta(items.get(position));
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                        .setIcon(R.drawable.rabano)
                                        .show();

                           }


                        }


        });






        //See if smoke is permited
        if(items.get(position).isAllowSmoking()){
            holder.imageSmoke.setImageResource(R.drawable.sifumar);
        }else{
            holder.imageSmoke.setImageResource(R.drawable.nofumarbueno);
        }

        //See if the eating is permited
        if(items.get(position).isAllowEating()){
            holder.imageEat.setImageResource(R.drawable.sicomer);
        }else{
            holder.imageEat.setImageResource(R.drawable.nocomer);
        }


        if(items.get(position).estadoConductor()){
            holder.estadoConductor.setImageResource(R.drawable.ic_volante);
        }else{
            holder.estadoConductor.setImageResource(R.drawable.ic_airline_seat_recline_normal_black_24dp);
        }
    }
    public void onViewAttachedToWindow(ContactoViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    protected void borraRuta(Route rselected) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("rutas");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final Route r1 = rselected;
        String email=rselected.getEmailUser();
        String endAddress=rselected.getEndAddress();
        final String referencia=rselected.getReferenceKey();



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Iterable i = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterador = i.iterator();
                while (iterador.hasNext()) {

                    DataSnapshot it =  iterador.next();
                    final Route r = it.getValue(Route.class);
                    if (r.getReferenceKey().equals(referencia)) {

                        ref.child(it.getKey()).setValue(null);

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

}