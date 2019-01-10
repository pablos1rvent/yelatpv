package com.yelatpv.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yelatpv.ClasesOBJ.Categoria;
import com.yelatpv.ClasesOBJ.Producto;
import com.yelatpv.R;

import java.util.List;
import java.util.Random;

/**
 * Created by pablosirvent on 15/5/18.
 */

public class ProductosTPVAdapter extends RecyclerView.Adapter<ProductosTPVAdapter.ViewHolder> {

    private List<Integer> colores;
    private List<Producto> productos;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public ProductosTPVAdapter(Context context, List<Producto> productos) {
        this.mInflater = LayoutInflater.from(context);
        this.productos = productos;

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

        Producto cat = productos.get(position);
        if(!cat.getImagen().equals("nosetted")){
            if(cat.getImagen() != ""){
                holder.myTextView.setVisibility(View.GONE);
                holder.miImagen.setVisibility(View.VISIBLE);
                holder.miImagen.setImageBitmap(StringToBitMap(cat.getImagen()));
            }else{
                holder.miImagen.setVisibility(View.GONE);
                holder.myView.setBackgroundColor(color);
            }
        }else{
            holder.miImagen.setVisibility(View.GONE);
            holder.myView.setBackgroundColor(color);
        }

        holder.myTextView.setText(cat.getNombreproducto());


    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return productos.size();
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
        ImageView miImagen;
        ViewHolder(View itemView) {
            super(itemView);
            myView = itemView.findViewById(R.id.color_categoria);
            myTextView = itemView.findViewById(R.id.nombre_categoria);
            miImagen = itemView.findViewById(R.id.imagen_general);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick_prods(view, getAdapterPosition());
        }
    }


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick_prods(View view, int position);
    }
}