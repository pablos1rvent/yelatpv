package com.yelatpv.Wizard;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.yelatpv.ClasesOBJ.Empresa;
import com.yelatpv.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


/**
 * Created by pablosirvent on 5/3/18.
 */

public class Paso1 extends Fragment {
    private OnSiguientePasoListener listener;
    Spinner spin;
    Empresa aux_e;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paso1, container, false);
        super.onCreateView(inflater, container, savedInstanceState);

        Bundle bundle = getArguments();
        aux_e = (Empresa) bundle.getSerializable("empresa");

        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length()>0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);
        spin = (Spinner)view.findViewById(R.id.paises_combo);

        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, countries);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        Button r = (Button) view.findViewById(R.id.btn_siguiente_paso1);

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String seleccionado = spin.getSelectedItem().toString();
                aux_e.setPais(seleccionado);
                listener.OnSiguientePaso(2, aux_e, null);
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
