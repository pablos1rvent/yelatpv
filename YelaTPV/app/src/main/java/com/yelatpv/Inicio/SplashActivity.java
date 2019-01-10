package com.yelatpv.Inicio;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.yelatpv.ClasesOBJ.BDEmpresa;
import com.yelatpv.Principal.Home;
import com.yelatpv.Principal.Login;

import java.util.concurrent.TimeUnit;

/**
 * Created by sirve on 25/02/2018.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));
        boolean empresa_registrada = false;
        BDEmpresa bdempresa = new BDEmpresa(this, "Empresa", null, 1);
        Cursor empresas_activas = bdempresa.getEmpresaCursor();



        if(empresas_activas.getCount() != 0){
            empresas_activas.close();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }else{
            empresas_activas.close();
            Intent intent = new Intent(this, RegistroEmpresa.class);
            startActivity(intent);
            finish();
        }

    }
}