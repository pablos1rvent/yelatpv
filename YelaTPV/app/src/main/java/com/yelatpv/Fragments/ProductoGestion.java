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

import com.yelatpv.Acciones.NuevaCategoria;
import com.yelatpv.Acciones.NuevoProducto;
import com.yelatpv.Adaptadores.CategoriaAdapter;
import com.yelatpv.Adaptadores.ProductoAdapter;
import com.yelatpv.ClasesOBJ.BDCategorias;
import com.yelatpv.ClasesOBJ.BDProductos;
import com.yelatpv.ClasesOBJ.Categoria;
import com.yelatpv.ClasesOBJ.Producto;
import com.yelatpv.Interfaces.OnProductoSelectedListener;
import com.yelatpv.Principal.Home;
import com.yelatpv.R;

import java.util.ArrayList;


public class ProductoGestion extends Fragment {

    private RecyclerView listarv;
    private ProductoAdapter adaptador;
    static Context context;
    private View vista;
    private int posicionPulsada;
    ArrayList<Producto> productos;
    private OnProductoSelectedListener onProductoSelectedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        context = getContext();


        vista = inflater.inflate(R.layout.fragment_productos, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("Productos");
        listarv = vista.findViewById(R.id.recycler_productos);


        productos = new ArrayList<>();
        cargaTodo();
        return vista;
    }
    @Override
    public void onAttach(Context contexto) {
        super.onAttach(context);
        onProductoSelectedListener = (OnProductoSelectedListener)this.getContext();
    }
    public void cargaTodo(){

        BDProductos bdproductos = new BDProductos(context, "productos", null, 1);
        productos = bdproductos.devuelveArrayListPorCursor(bdproductos.getProductosCursor());
        if(productos.size() > 0){
            RelativeLayout rl = vista.findViewById(R.id.vacio_layout);
            rl.setVisibility(View.GONE);
        }
        adaptador = new ProductoAdapter(productos);
        adaptador.miClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicionPulsada = listarv.getChildAdapterPosition(view);
                onProductoSelectedListener.onProductoSelected(productos.get(posicionPulsada), posicionPulsada);
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
            case R.id.add_producto:
                if(Home.permisos_administracion){
                    Intent myIntent = new Intent(ProductoGestion.this.getActivity(), NuevoProducto.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    startActivity(myIntent);
                }else{
                    Toast.makeText(context, "No tienes permisos de Administración, lo siento", Toast.LENGTH_SHORT).show();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_producto, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


}