package com.beet.application.travelue;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;
import java.util.Map;
import java.util.Random;


/**
 * Created by Eduardo on 24/11/2016.
 */
public class cardChats extends RecyclerView.Adapter<cardChats.cardChatViewHolder> {

    private ArrayList<Route> items;
    public Context context;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Chats");


    public class cardChatViewHolder extends RecyclerView.ViewHolder {

        public CardView cv;
        public TextView nombre;
        public TextView estado;
        public TextView origen;
        public TextView destino;
        public TextView mail;
        public TextView numberPassengers;
        public TextView hours;
        public TextView calendar;
        public ImageView imagen;
        public ImageView estadoConductor;
        public ImageView imageSmoke;
        public ImageView imageEat;








        public cardChatViewHolder(View itemView) {
            super(itemView);


            cv = (CardView) itemView.findViewById(R.id.card_view);


            nombre = (TextView) itemView.findViewById(R.id.tvNombre);
            numberPassengers = (TextView) itemView.findViewById(R.id.tvNumberPassengers);
            estado = (TextView) itemView.findViewById(R.id.tvEstado);
            origen = (TextView) itemView.findViewById(R.id.tvOrigen);
            destino = (TextView) itemView.findViewById(R.id.tvDestino);
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


    public cardChats(Context context, ArrayList<Route> items) {
        this.context = context;
        this.items = items;
    }
    //

    @Override
    public cardChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_chats, parent, false);

        return new cardChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final cardChatViewHolder holder, final int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (items.get(position).getFotoPerfil() != null) {

            new DownloadImageTask(holder.imagen).execute(items.get(position).getFotoPerfil());
        }



        holder.nombre.setText(items.get(position).getNombreUser());
        holder.estado.setText(items.get(position).getTypeOfUser());
        holder.origen.setText(items.get(position).getStartAddress());
        holder.destino.setText(items.get(position).getEndAddress());
        String nombre = items.get(position).getEmailUser().replace(".","");

        holder.numberPassengers.setText(String.valueOf(items.get(position).getNumberOfPasangers()));


        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String nombre = items.get(position).getEmailUser().replace(".","");
                Map<String,Object> map = new HashMap<String, Object>();

                map.put(nombre,"");





                    Intent intent = new Intent(cardChats.this.context, ChatRoom.class);
                    intent.putExtra("room_name", nombre);
                    intent.putExtra("user_name", user.getDisplayName());
                    context.startActivity(intent);





            }




        });




        //See if smoke is permited
        if (items.get(position).isAllowSmoking()) {
            holder.imageSmoke.setImageResource(R.drawable.sifumar);
        } else {
            holder.imageSmoke.setImageResource(R.drawable.nofumar);
        }

        //See if the eating is permited
        if (items.get(position).isAllowEating()) {
            holder.imageEat.setImageResource(R.drawable.sicomer);
        } else {
            holder.imageEat.setImageResource(R.drawable.nocomer);
        }


        if (items.get(position).estadoConductor()) {
            holder.estadoConductor.setImageResource(R.drawable.ic_icon);
        } else {
            holder.estadoConductor.setImageResource(R.drawable.pasajerogris);
        }
    }

    public void onViewAttachedToWindow(cardChatViewHolder holder) {
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
        String email = rselected.getEmailUser();
        String endAddress = rselected.getEndAddress();
        final String referencia = rselected.getReferenceKey();



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Iterable i = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterador = i.iterator();
                while (iterador.hasNext()) {

                    DataSnapshot it = iterador.next();
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

    String getCadenaAlfanumAleatoria (int longitud){
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while ( i < longitud){
            char c = (char)r.nextInt(255);
            if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
                cadenaAleatoria += c;
                i ++;
            }
        }
        return cadenaAleatoria;
    }

}