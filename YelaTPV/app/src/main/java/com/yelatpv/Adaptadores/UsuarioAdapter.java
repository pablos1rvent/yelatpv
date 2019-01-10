package com.yelatpv.Adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yelatpv.ClasesOBJ.Producto;
import com.yelatpv.ClasesOBJ.Usuario;
import com.yelatpv.Holders.ProductoHolder;
import com.yelatpv.Holders.UsuarioHolder;
import com.yelatpv.R;

import java.util.ArrayList;

/**
 * Created by alumno on 08/11/2017.
 */

public class UsuarioAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private ArrayList<Usuario> listaCont;
    private View.OnClickListener listener_uno;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.linea_recycler_usuarios, parent, false);

        UsuarioHolder holder = new UsuarioHolder(vista);
        vista.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((UsuarioHolder)holder).bind(listaCont.get(position));
    }

    @Override
    public void onClick(View view) {
        if (listener_uno != null) {
            listener_uno.onClick(view);
        }
    }

    public void miClickListener(View.OnClickListener listener) {
        this.listener_uno = listener;
    }

    @Override
    public int getItemCount() {
        return listaCont.size();
    }



    public UsuarioAdapter(ArrayList<Usuario> listaCont) {
        this.listaCont = listaCont;
    }
}
