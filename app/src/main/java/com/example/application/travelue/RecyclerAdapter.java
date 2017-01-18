package com.example.application.travelue;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ~marco~ on 18/01/2017.
 */

    import android.support.design.widget.Snackbar;
    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private String[] titles = {"Chapter One",
                "Chapter Two",
                "Chapter Three",
                "Chapter Four",
                "Chapter Five",
                "Chapter Six",
                "Chapter Seven",
                "Chapter Eight"};

        private String[] details = {"Item one details",
                "Item two details", "Item three details",
                "Item four details", "Item file details",
                "Item six details", "Item seven details",
                "Item eight details"};

        class ViewHolder extends RecyclerView.ViewHolder{

            public int currentItem;
            public ImageView itemImageUser;
            public ImageView itemImageCar;
            public TextView itemTitle;
            public TextView itemDetail;

            public ViewHolder(View itemView) {
                super(itemView);
                itemImageUser = (ImageView)itemView.findViewById(R.id.ivUsuario);
                itemImageCar = (ImageView)itemView.findViewById(R.id.ivCoche);
                itemTitle = (TextView)itemView.findViewById(R.id.tvUser);
                itemDetail =
                        (TextView)itemView.findViewById(R.id.item_detail);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        int position = getAdapterPosition();

                        Snackbar.make(v, "Click detected on item " + position,
                                Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                });
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.activity_main, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.itemTitle.setText(titles[i]);
            viewHolder.itemDetail.setText(details[i]);
            viewHolder.itemImage.setImageResource(images[i]);
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }
}


