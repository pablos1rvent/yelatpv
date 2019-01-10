package com.yelatpv.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yelatpv.ClasesOBJ.Categoria;
import com.yelatpv.Fragments.CategoriaGestion;
import com.yelatpv.Holders.CategoriaHolder;
import com.yelatpv.Interfaces.OnCategoriaSelectedListener;
import com.yelatpv.R;

import java.util.ArrayList;

/**
 * Created by alumno on 08/11/2017.
 */

public class CategoriaAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private ArrayList<Categoria> listaCont;
    private View.OnClickListener listener_uno;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.linea_recycler_categorias, parent, false);
        vista.setOnClickListener(this);
        CategoriaHolder holder = new CategoriaHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CategoriaHolder)holder).bind(listaCont.get(position));
    }

    @Override
    public void onClick(View view) {
        if (listener_uno != null) {
            listener_uno.onClick(view);
        }
    }

    @Override
    public int getItemCount() {
        return listaCont.size();
    }



    public CategoriaAdapter(ArrayList<Categoria> listaCont) {
        this.listaCont = listaCont;
    }
}
