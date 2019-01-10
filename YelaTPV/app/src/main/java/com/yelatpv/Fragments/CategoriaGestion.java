package com.yelatpv.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.yelatpv.Acciones.NuevaCategoria;
import com.yelatpv.Adaptadores.CategoriaAdapter;
import com.yelatpv.ClasesOBJ.BDCategorias;
import com.yelatpv.ClasesOBJ.Categoria;
import com.yelatpv.Principal.Home;
import com.yelatpv.R;

import java.util.ArrayList;


public class CategoriaGestion extends Fragment {


    private RecyclerView listarv;
    private CategoriaAdapter adaptador;
    static Context context;
    private View vista;
    ArrayList<Categoria> categorias;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        vista = inflater.inflate(R.layout.fragment_categorias, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("Categorías");
        listarv = vista.findViewById(R.id.recycler_categorias);
        context = getContext();
        categorias = new ArrayList<>();
        BDCategorias bdcategorias = new BDCategorias(context, "categorias", null, 1);
        categorias = bdcategorias.devuelveArrayListPorCursor(bdcategorias.getCategoriasCursor());
        adaptador = new CategoriaAdapter(categorias);
        listarv.setAdapter(adaptador);
        LinearLayoutManager lLManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listarv.setHasFixedSize(true);
        listarv.setLayoutManager(lLManager);

        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        BDCategorias bdcategorias = new BDCategorias(context, "categorias", null, 1);
        categorias = bdcategorias.devuelveArrayListPorCursor(bdcategorias.getCategoriasCursor());
        if(categorias.size() > 0){
            RelativeLayout rl = vista.findViewById(R.id.vacio_layout);
            rl.setVisibility(View.GONE);
        }
        adaptador = new CategoriaAdapter(categorias);
        adaptador.notifyDataSetChanged();
        listarv.setAdapter(adaptador);
        LinearLayoutManager lLManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listarv.setHasFixedSize(true);
        listarv.setLayoutManager(lLManager);
    }

    private void contactossetter(RecyclerView listarv, CategoriaAdapter adaptador){
        listarv.setAdapter(adaptador);
        LinearLayoutManager lLManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listarv.setHasFixedSize(true);
        listarv.setLayoutManager(lLManager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_categoria:
                if(Home.permisos_administracion){
                    Intent myIntent = new Intent(CategoriaGestion.this.getActivity(), NuevaCategoria.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    startActivity(myIntent);
                }else{
                    Toast.makeText(context, "No tienes permisos de Administración", Toast.LENGTH_SHORT).show();
                }
                
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_categoria, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}