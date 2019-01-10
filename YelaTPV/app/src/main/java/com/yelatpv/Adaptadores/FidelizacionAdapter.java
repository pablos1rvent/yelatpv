package com.yelatpv.Adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yelatpv.ClasesOBJ.Fidelizacion;
import com.yelatpv.Holders.FidelizacionHolder;
import com.yelatpv.R;

import java.util.ArrayList;

/**
 * Created by alumno on 08/11/2017.
 */

public class FidelizacionAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private ArrayList<Fidelizacion> listaCont;
    private View.OnClickListener listener_uno;
    private View.OnLongClickListener listener_dos;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.linea_recycler_fidelizacion, parent, false);

        FidelizacionHolder holder = new FidelizacionHolder(vista);
        vista.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((FidelizacionHolder)holder).bind(listaCont.get(position));
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
    public void miLongListener(View.OnLongClickListener listener) {
        this.listener_dos = listener;
    }

    @Override
    public int getItemCount() {
        return listaCont.size();
    }



    public FidelizacionAdapter(ArrayList<Fidelizacion> listaCont) {
        this.listaCont = listaCont;
    }

}
