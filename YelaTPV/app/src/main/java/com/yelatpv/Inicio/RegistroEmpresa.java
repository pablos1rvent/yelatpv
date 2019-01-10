package com.yelatpv.Inicio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yelatpv.R;
import com.yelatpv.Wizard.WizardEmpresa;

/**
 * Created by sirve on 25/02/2018.
 */

public class RegistroEmpresa extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registroempresa);
        Button boton_vamos = (Button)findViewById(R.id.boton_empezar_config);
        boton_vamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WizardEmpresa.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
