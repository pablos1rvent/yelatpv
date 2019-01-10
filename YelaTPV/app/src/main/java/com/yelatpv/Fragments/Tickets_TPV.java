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

import com.yelatpv.Acciones.NuevoProducto;
import com.yelatpv.Adaptadores.ProductoAdapter;
import com.yelatpv.Adaptadores.TicketAdapter;
import com.yelatpv.ClasesOBJ.BDProductos;
import com.yelatpv.ClasesOBJ.BDTickets;
import com.yelatpv.ClasesOBJ.Producto;
import com.yelatpv.ClasesOBJ.Tickets;
import com.yelatpv.Interfaces.OnProductoSelectedListener;
import com.yelatpv.Interfaces.OnTicketSelectedListener;
import com.yelatpv.Principal.Home;
import com.yelatpv.R;

import java.util.ArrayList;


public class Tickets_TPV extends Fragment {

    private RecyclerView listarv;
    private TicketAdapter adaptador;
    static Context context;
    private View vista;
    private int posicionPulsada;
    ArrayList<Tickets> tickets;
    private OnTicketSelectedListener onTicketSelectedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        context = getContext();


        vista = inflater.inflate(R.layout.fragment_tickets_tpv, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("Tickets TPV");
        listarv = vista.findViewById(R.id.recycler_tickets);

        tickets = new ArrayList<>();
        cargaTodo();
        return vista;
    }
    @Override
    public void onAttach(Context contexto) {
        super.onAttach(context);
        onTicketSelectedListener = (OnTicketSelectedListener)this.getContext();
    }
    public void cargaTodo(){

        BDTickets bdtickets = new BDTickets(context, "tickets", null, 1);
        tickets = bdtickets.devuelveArrayListPorCursor(bdtickets.getTicketsCursor());
        if(tickets.size() > 0){
            RelativeLayout rl = vista.findViewById(R.id.vacio_layout);
            rl.setVisibility(View.GONE);
        }
        adaptador = new TicketAdapter(tickets);
        adaptador.miClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicionPulsada = listarv.getChildAdapterPosition(view);
                onTicketSelectedListener.onTicketSelected(tickets.get(posicionPulsada), posicionPulsada);
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
            case R.id.add_ticket:
                ((Home) getActivity()).cambiarFragmentDesdeOtroFragment(1);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_tickets, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

}