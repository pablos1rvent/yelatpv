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
import android.widget.Toast;

import com.yelatpv.ClasesOBJ.Empresa;
import com.yelatpv.R;


/**
 * Created by pablosirvent on 5/3/18.
 */

public class Paso2 extends Fragment {
    private OnSiguientePasoListener listener;
    Empresa aux_e;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paso2, container, false);
        super.onCreateView(inflater, container, savedInstanceState);

        Bundle bundle = getArguments();
        aux_e = (Empresa) bundle.getSerializable("empresa");
        Button r = (Button) view.findViewById(R.id.btn_siguiente_paso2);


        final EditText razonsocial = view.findViewById(R.id.razonsocial);
        final EditText cifnif = view.findViewById(R.id.cifnif);
        final EditText direccion = view.findViewById(R.id.direccion);
        final EditText provincia = view.findViewById(R.id.provincia);
        final EditText ciudad = view.findViewById(R.id.ciudad);
        final EditText codpostal = view.findViewById(R.id.codpostal);
        final EditText telefono = view.findViewById(R.id.telefono);

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error = false;
                if(razonsocial.getText().toString().equals("")){
                    error = true;
                }
                if(cifnif.getText().toString().equals("")){
                    error = true;
                }
                if(direccion.getText().toString().equals("")){
                    error = true;
                }
                if(provincia.getText().toString().equals("")){
                    error = true;
                }
                if(ciudad.getText().toString().equals("")){
                    error = true;
                }
                if(codpostal.getText().toString().equals("")){
                    error = true;
                }
                if(telefono.getText().toString().equals("")){
                    error = true;
                }


                if(error){
                    Toast.makeText(getContext(), "Rellena todos los campos, por favor", Toast.LENGTH_SHORT).show();
                }else{
                    aux_e.setRazonsocial(razonsocial.getText().toString());
                    aux_e.setNifcif(cifnif.getText().toString());
                    aux_e.setDireccion(direccion.getText().toString());
                    aux_e.setProvincia(provincia.getText().toString());
                    aux_e.setCiudad(ciudad.getText().toString());
                    aux_e.setCodigopostal(codpostal.getText().toString());
                    aux_e.setTelefono(telefono.getText().toString());

                    listener.OnSiguientePaso(3, aux_e, null);
                }
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
