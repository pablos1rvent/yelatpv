package com.yelatpv.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yelatpv.Acciones.NuevaFidelizacion;
import com.yelatpv.Adaptadores.FidelizacionAdapter;
import com.yelatpv.ClasesOBJ.BDFidelizacion;
import com.yelatpv.Interfaces.OnFidelizacionSelectedListener;
import com.yelatpv.Principal.Home;
import com.yelatpv.R;

import java.util.ArrayList;


public class Fidelizacion extends Fragment {

    private RecyclerView listarv;
    private FidelizacionAdapter adaptador;
    static Context context;
    private View vista;
    private int posicionPulsada;
    ArrayList<com.yelatpv.ClasesOBJ.Fidelizacion> fidelizacion;
    private OnFidelizacionSelectedListener onFidelizacionSelectedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        context = getContext();


        vista = inflater.inflate(R.layout.fragment_fidelizacion, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("Fidelización");
        listarv = vista.findViewById(R.id.recycler_fidelizacion);

        fidelizacion = new ArrayList<>();
        cargaTodo();
        return vista;
    }
    @Override
    public void onAttach(Context contexto) {
        super.onAttach(context);
        onFidelizacionSelectedListener = (OnFidelizacionSelectedListener)this.getContext();
    }
    public void cargaTodo(){

        final BDFidelizacion bdfidelizacion = new BDFidelizacion(context, "fidelizacion", null, 1);
        fidelizacion = bdfidelizacion.devuelveArrayListPorCursor(bdfidelizacion.getFidelizacionCursor());
        if(fidelizacion.size() > 0){
            RelativeLayout rl = vista.findViewById(R.id.vacio_layout);
            rl.setVisibility(View.GONE);
        }
        adaptador = new FidelizacionAdapter(fidelizacion);


        adaptador.miClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicionPulsada = listarv.getChildAdapterPosition(view);
                onFidelizacionSelectedListener.onFidelizacionSelected(fidelizacion.get(posicionPulsada), posicionPulsada);
            }
        });
        adaptador.notifyDataSetChanged();
        listarv.setAdapter(adaptador);
        LinearLayoutManager lLManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listarv.setHasFixedSize(true);
        listarv.setLayoutManager(lLManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        cargaTodo();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_fidelizacion:
                Intent myIntent = new Intent(Fidelizacion.this.getActivity(), NuevaFidelizacion.class);
                //myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_fidelizacion, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


}