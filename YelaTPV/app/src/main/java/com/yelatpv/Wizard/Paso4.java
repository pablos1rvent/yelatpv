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
import com.yelatpv.ClasesOBJ.Usuario;
import com.yelatpv.R;


/**
 * Created by pablosirvent on 5/3/18.
 */

public class Paso4 extends Fragment {
    private OnSiguientePasoListener listener;
    Empresa aux_e;
    EditText correo;
    EditText usuario;
    EditText password;
    EditText pin1;
    EditText pin2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paso4, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = getArguments();
        aux_e = (Empresa) bundle.getSerializable("empresa");
        Button r = (Button) view.findViewById(R.id.btn_finalizar);

        correo =  (EditText)view.findViewById(R.id.correo);
        usuario =  (EditText)view.findViewById(R.id.usuario);
        password =  (EditText)view.findViewById(R.id.password);
        pin1 =  (EditText)view.findViewById(R.id.pin1);
        pin2 =  (EditText)view.findViewById(R.id.pin2);

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin1.getText().toString().equals("") || pin2.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Por seguridad, debes introducir un PIN de acceso", Toast.LENGTH_SHORT).show();
                }else{
                    if(pin1.getText().toString().equals(pin2.getText().toString())){
                        listener.OnSiguientePaso(5, aux_e, new Usuario(1, correo.getText().toString(), usuario.getText().toString(), password.getText().toString(),  pin1.getText().toString(), true));
                    }else{
                        Toast.makeText(getContext(), "Las contraseñas PIN no son iguales", Toast.LENGTH_SHORT).show();
                    }
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
