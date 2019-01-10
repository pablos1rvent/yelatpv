package com.yelatpv.Wizard;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.yelatpv.ClasesOBJ.Empresa;
import com.yelatpv.R;


/**
 * Created by pablosirvent on 5/3/18.
 */

public class Paso3 extends Fragment {
    private OnSiguientePasoListener listener;
    Empresa aux_e;
    EditText porcentaje_iva;
    Boolean mismoiva_para_todos_los_productos = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paso3, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = getArguments();
        aux_e = (Empresa) bundle.getSerializable("empresa");
        Button r = (Button) view.findViewById(R.id.btn_finalizar);

        porcentaje_iva =  (EditText)view.findViewById(R.id.iva_porcentaje);
        final RadioButton mismoiva = view.findViewById(R.id.mismoiva);
        mismoiva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                porcentaje_iva.setVisibility(View.VISIBLE);
                mismoiva_para_todos_los_productos = true;
            }
        });

        RadioButton iva_distinto = view.findViewById(R.id.ivaespecifico);
        iva_distinto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                porcentaje_iva.setVisibility(View.GONE);
                mismoiva_para_todos_los_productos = false;
            }
        });

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aux_e.setIva_general(mismoiva_para_todos_los_productos);
                if(mismoiva_para_todos_los_productos) {
                    aux_e.setIva(Float.parseFloat(porcentaje_iva.getText().toString()));
                }

                listener.OnSiguientePaso(4, aux_e, null);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (OnSiguientePasoListener) context;
        }catch(ClassCastException e){}
    }
}
