package com.yelatpv.Wizard;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.yelatpv.ClasesOBJ.BDEmpresa;
import com.yelatpv.ClasesOBJ.BDUsuarios;
import com.yelatpv.ClasesOBJ.Empresa;
import com.yelatpv.ClasesOBJ.Usuario;
import com.yelatpv.Principal.Home;
import com.yelatpv.R;


public class WizardEmpresa extends AppCompatActivity implements OnSiguientePasoListener{
    FragmentManager fM;
    FragmentTransaction fT;
    Empresa e;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wizardempresa);
        e = new Empresa();
        /*Sólo soportamos España por ahora */
        e.setPais("Spain");
        Bundle bundle = new Bundle();
        bundle.putSerializable("empresa", e);

        fM = getSupportFragmentManager();
        fT = fM.beginTransaction();

        /*Selección del paso al darle al botón de registrar empresa*/
        // Como sólo soportamos España, es tontería
        Paso2 fragment2 = new Paso2();
        fragment2.setArguments(bundle);
        fT.add(R.id.wizard_contenedor, fragment2);
        fT.commit();
    }

    @Override
    public void OnSiguientePaso(int pasosiguiente, Empresa e, Usuario u) {
        switch (pasosiguiente){
            case 1:

            case 2:
                Paso2 paso2 = new Paso2();
                Bundle bundle_2 = new Bundle();
                Empresa e_2 = e;
                bundle_2.putSerializable("empresa", e_2);
                paso2.setArguments(bundle_2);
                FragmentManager FM = getSupportFragmentManager();
                FragmentTransaction FT= FM.beginTransaction();
                FT.replace(R.id.wizard_contenedor, paso2);
                FT.addToBackStack(null);
                FT.commit();
                break;
            case 3:
                Paso3 paso3 = new Paso3();
                Bundle bundle_3 = new Bundle();
                Empresa e_3 = e;
                bundle_3.putSerializable("empresa", e_3);
                paso3.setArguments(bundle_3);

                FragmentManager FM2 = getSupportFragmentManager();
                FragmentTransaction FT2= FM2.beginTransaction();
                FT2.replace(R.id.wizard_contenedor, paso3);
                FT2.addToBackStack(null);
                FT2.commit();
                break;
            case 4:
                Paso4 paso4 = new Paso4();
                Bundle bundle_4 = new Bundle();
                Empresa e_4 = e;
                bundle_4.putSerializable("empresa", e_4);
                paso4.setArguments(bundle_4);

                FragmentManager FM4 = getSupportFragmentManager();
                FragmentTransaction FT4= FM4.beginTransaction();
                FT4.replace(R.id.wizard_contenedor, paso4);
                FT4.addToBackStack(null);
                FT4.commit();

                break;
            case 5:
                BDEmpresa bdempresa = new BDEmpresa(this, "Empresa", null, 1);
                bdempresa.InsertaEmpresa(e);
                BDUsuarios bdusuarios = new BDUsuarios(this, "Usuarios", null, 1);
                bdusuarios.InsertaUsuario(u);
                Intent intent = new Intent(this, Home.class);
                intent.putExtra("NEW_EMPRESA", true);
                intent.putExtra("USER_ID", u.getIdusuario());
                startActivity(intent);
                finish();
                break;
        }

    }
}
