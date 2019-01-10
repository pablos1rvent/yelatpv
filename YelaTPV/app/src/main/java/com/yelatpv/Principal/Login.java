package com.yelatpv.Principal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.yelatpv.ClasesOBJ.BDUsuarios;
import com.yelatpv.ClasesOBJ.Usuario;
import com.yelatpv.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by pablosirvent on 22/5/18.
 */

public class Login extends AppCompatActivity {
    Boolean exito = false;
    EditText campo_pin;
    Usuario userselected;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        BDUsuarios bdusuarios = new BDUsuarios(getApplicationContext(), "Usuarios", null, 1);
        final ArrayList<Usuario> lista_usuarios = bdusuarios.devuelveArrayListPorCursor(bdusuarios.getUsuarioscursor());
        ArrayList<String> usuarios_lista_strings = new ArrayList<>();
        for (int i = 0; i < lista_usuarios.size(); i++) {
            Usuario u = lista_usuarios.get(i);
            usuarios_lista_strings.add(u.getNombreusuario());
        }

        Spinner spinner = (Spinner) findViewById(R.id.usuariosspinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.usuarios_linea_login, usuarios_lista_strings);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                userselected = lista_usuarios.get(position);
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        spinner.setAdapter(adapter);


        Button boton_limpiar = findViewById(R.id.boton_limpiar);
        boton_limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campo_pin.setText("");
            }
        });
        campo_pin = (EditText) findViewById(R.id.campo_pass);
        campo_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                BDUsuarios bdusuarios = new BDUsuarios(getApplicationContext(), "Usuarios", null, 1);
                Cursor usuario_cursor = bdusuarios.getUsuarioCursorPorID(1);

                String campopin_bd = "nodebesalir";
                if (usuario_cursor.moveToFirst()){
                    while(!usuario_cursor.isAfterLast()){
                        campopin_bd = usuario_cursor.getString(usuario_cursor.getColumnIndex("pinacceso"));
                        usuario_cursor.moveToNext();
                    }
                }
                usuario_cursor.close();
                String introducido = campo_pin.getText().toString();


                if (introducido.equals(userselected.getPinacceso().toString())) {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.putExtra("IDUSER_LOG", userselected.getIdusuario().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
