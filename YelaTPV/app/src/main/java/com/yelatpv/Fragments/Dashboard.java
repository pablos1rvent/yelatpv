package com.yelatpv.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yelatpv.Principal.Home;
import com.yelatpv.R;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


public class Dashboard extends Fragment {

    CardView vender;
    CardView tickets;
    CardView addproducto;
    CardView fidelizacion;
    CardView ajustes;
    boolean nuevo = false;

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    private static final String SHOWCASE_ID = "1";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dashboard, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("Dashboard");
        vender = (CardView)view.findViewById(R.id.vender);
        tickets = (CardView)view.findViewById(R.id.tickets);
        addproducto = (CardView)view.findViewById(R.id.add_producto_boton_c);
        fidelizacion = (CardView)view.findViewById(R.id.fidelizacion_btn);

        if(this.isNuevo()){
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(200); // half second between each showcase view
            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), SHOWCASE_ID);
            sequence.setConfig(config);
            sequence.addSequenceItem(vender,
                    "Aquí puedes empezar vendiendo", "Siguiente");
            sequence.addSequenceItem(tickets,
                    "Puedes ver tus tickets", "Siguiente");
            sequence.addSequenceItem(addproducto,
                    "Añadir un producto", "Siguiente");
            sequence.addSequenceItem(fidelizacion,
                    "O simplemente crear una tarjeta regalo, te animas?", "¡Claro, vamos a vender!");
            sequence.start();
        }




        vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home) getActivity()).cambiarFragmentDesdeOtroFragment(1);
            }
        });
        tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home) getActivity()).cambiarFragmentDesdeOtroFragment(2);
            }
        });

        addproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home) getActivity()).cambiarFragmentDesdeOtroFragment(4);
            }
        });
        fidelizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home) getActivity()).cambiarFragmentDesdeOtroFragment(3);
            }
        });
        return view;
    }

}