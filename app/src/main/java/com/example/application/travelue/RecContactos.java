

package com.example.application.travelue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;
import java.util.ArrayList;


    /**
     * Created by Eduardo on 24/11/2016.
 */
public class RecContactos extends RecyclerView.Adapter<RecContactos.ContactoViewHolder>  {

    private ArrayList<Route> items;
    public Context context;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

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
    public void onBindViewHolder(ContactoViewHolder holder, int position) {
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


}