package com.example.application.travelue;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Eduardo on 24/11/2016.
 */

public class RecContactos extends RecyclerView.Adapter<RecContactos.ContactoViewHolder>  {

    private ArrayList<Usuario> items;
    public Context context;



    public class ContactoViewHolder extends RecyclerView.ViewHolder {

    public TextView nombre;
    public TextView telefono;
        public ImageView imagen;
        public ImageView imagen2;


    public ContactoViewHolder(View itemView) {
        super(itemView);
        nombre = (TextView) itemView.findViewById(R.id.item_title);
        telefono = (TextView) itemView.findViewById(R.id.item_detail);
        imagen = (ImageView) itemView.findViewById(R.id.item_image);
        imagen2 = (ImageView) itemView.findViewById(R.id.item_image2);
        


        }
    }






    public RecContactos(Context context,ArrayList<Usuario> items){
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


        holder.nombre.setText(items.get(position).getNombre());
        holder.telefono.setText(items.get(position).getApellido());
        holder.imagen.setImageResource(R.drawable.nophoto);
        holder.imagen2.setImageResource(R.drawable.nophoto);



    }
    public void onViewAttachedToWindow(ContactoViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }





}
