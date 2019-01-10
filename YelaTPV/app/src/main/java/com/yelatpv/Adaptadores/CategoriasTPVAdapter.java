package com.yelatpv.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yelatpv.ClasesOBJ.Categoria;
import com.yelatpv.R;

import java.util.List;
import java.util.Random;

/**
 * Created by pablosirvent on 15/5/18.
 */

public class CategoriasTPVAdapter extends RecyclerView.Adapter<CategoriasTPVAdapter.ViewHolder> {

    private List<Integer> colores;
    private List<Categoria> categorias;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public CategoriasTPVAdapter(Context context, List<Categoria> categorias) {
        this.mInflater = LayoutInflater.from(context);
        this.categorias = categorias;

    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.categorias_horizontales, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        //int color = colores.get(position);

        Categoria cat = categorias.get(position);
        holder.myView.setBackgroundColor(color);
        holder.myTextView.setText(cat.getNombrecategoria());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return categorias.size();
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        //return categorias.get(id);
        return "";
    }









    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View myView;
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myView = itemView.findViewById(R.id.color_categoria);
            myTextView = itemView.findViewById(R.id.nombre_categoria);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}