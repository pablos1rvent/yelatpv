package com.yelatpv.Fragments;

import android.content.Context;
import android.content.Intent;
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

import com.yelatpv.Acciones.NuevoProducto;
import com.yelatpv.Acciones.NuevoUsuario;
import com.yelatpv.Adaptadores.ProductoAdapter;
import com.yelatpv.Adaptadores.UsuarioAdapter;
import com.yelatpv.ClasesOBJ.BDProductos;
import com.yelatpv.ClasesOBJ.BDUsuarios;
import com.yelatpv.ClasesOBJ.Producto;
import com.yelatpv.ClasesOBJ.Usuario;
import com.yelatpv.Interfaces.OnProductoSelectedListener;
import com.yelatpv.Interfaces.OnUsuarioSelectedListener;
import com.yelatpv.Principal.Home;
import com.yelatpv.R;

import java.util.ArrayList;


public class UsuarioGestion extends Fragment {

    private RecyclerView listarv;
    private UsuarioAdapter adaptador;
    static Context context;
    private View vista;
    private int posicionPulsada;
    ArrayList<Usuario> usuarios;
    private OnUsuarioSelectedListener onUsuarioSelectedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        context = getContext();


        vista = inflater.inflate(R.layout.fragment_usuarios, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("Usuarios del sistema");
        listarv = vista.findViewById(R.id.recycler_usuarios);

        usuarios = new ArrayList<>();
        cargaTodo();
        return vista;
    }
    @Override
    public void onAttach(Context contexto) {
        super.onAttach(context);
        onUsuarioSelectedListener = (OnUsuarioSelectedListener)this.getContext();
    }
    public void cargaTodo(){

        BDUsuarios bdusuarios = new BDUsuarios(getContext(), "Usuarios", null, 1);
        usuarios = bdusuarios.devuelveArrayListPorCursor(bdusuarios.getUsuarioscursor());
        if(usuarios.size() > 0){
            RelativeLayout rl = vista.findViewById(R.id.vacio_layout);
            rl.setVisibility(View.GONE);
        }
        adaptador = new UsuarioAdapter(usuarios);
        adaptador.miClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicionPulsada = listarv.getChildAdapterPosition(view);
                onUsuarioSelectedListener.onUsuarioSelected(usuarios.get(posicionPulsada), posicionPulsada);
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
            case R.id.add_usuario:
                Intent myIntent = new Intent(UsuarioGestion.this.getActivity(), NuevoUsuario.class);
                //myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_usuario, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


}