package com.yelatpv.Acciones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yelatpv.ClasesOBJ.BDCategorias;
import com.yelatpv.ClasesOBJ.BDFidelizacion;
import com.yelatpv.ClasesOBJ.BDUsuarios;
import com.yelatpv.ClasesOBJ.Categoria;
import com.yelatpv.ClasesOBJ.Fidelizacion;
import com.yelatpv.ClasesOBJ.Usuario;
import com.yelatpv.R;

import java.util.ArrayList;

public class NuevoUsuario extends AppCompatActivity{

    boolean modo_edicion = false;
    String id_usuario = "";
    Usuario u_aux;


    EditText mail;
    EditText nombreusuario;
    EditText password;
    EditText pinacceso;
    CheckBox esOwner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_usuario);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        mail = findViewById(R.id.mail);
        nombreusuario = findViewById(R.id.nombreusuario);
        password = findViewById(R.id.password1);
        pinacceso = findViewById(R.id.pinacceso);
        esOwner = findViewById(R.id.esOwner);


        if(bd != null)
        {
            id_usuario = (String) bd.get("idusuario");
            BDUsuarios bdusuarios = new BDUsuarios(getApplicationContext(), "Usuarios", null, 1);
            Usuario u = bdusuarios.devuelveArrayListPorCursor(bdusuarios.getUsuarioCursorPorID(Integer.parseInt(id_usuario))).get(0);
            u_aux = u;
            mail.setText(u.getMail());
            nombreusuario.setText(u.getNombreusuario());
            if(u.getEsowner()){
                esOwner.setChecked(true);
            }
            modo_edicion = true;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nuevo_usuario_menu, menu);
        MenuItem botonborrar = menu.findItem(R.id.delete_usuario);
        if(modo_edicion){
            botonborrar.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        BDUsuarios bdusuarios = new BDUsuarios(getApplicationContext(), "Usuarios", null, 1);
        switch(item.getItemId()){
            case R.id.add_boton_usuario:



                String correo_insert = mail.getText().toString();
                String nombreuser_insert = nombreusuario.getText().toString();
                String pass_insert = password.getText().toString();
                String pinacceso_insert = pinacceso.getText().toString();
                Boolean esowner = false;


                boolean valido = true;
                if(correo_insert.equals("")){
                    valido = false;
                }
                if(nombreuser_insert.equals("")){
                    valido = false;
                }

                if(!modo_edicion){
                    if(pass_insert.equals("")){
                        valido = false;
                    }
                    if(pinacceso_insert.equals("")){
                        valido = false;
                    }
                }

                if(valido){
                    if(modo_edicion){

                        if(password.getText().length() > 0){
                            pass_insert = password.getText().toString();
                        }else{
                            pass_insert = u_aux.getPassword();
                        }
                        if(pinacceso.getText().length() > 0){
                            pinacceso_insert = pinacceso.getText().toString();
                        }else{
                            pinacceso_insert = u_aux.getPinacceso();
                        }
                        if(esOwner.isChecked()){
                            esowner = true;
                        }
                        Usuario aux_insert = new Usuario();
                        aux_insert.setIdusuario(u_aux.getIdusuario());
                        aux_insert.setMail(correo_insert);
                        aux_insert.setNombreusuario(nombreuser_insert);
                        aux_insert.setPassword(pass_insert);
                        aux_insert.setPinacceso(pinacceso_insert);
                        aux_insert.setEsOwner(esowner);
                        bdusuarios.ActualizaUsuario(aux_insert);

                    }else{
                        boolean sigue = true;
                        if(password.getText().length() > 0){
                            pass_insert = password.getText().toString();
                        }else{
                            sigue = false;
                            Toast.makeText(this, "Inserta una contraseña, por favor", Toast.LENGTH_SHORT).show();
                        }
                        if(pinacceso.getText().length() > 0){
                            pinacceso_insert = pinacceso.getText().toString();
                        }else{
                            sigue = false;
                            Toast.makeText(this, "Inserta un pin, por favor", Toast.LENGTH_SHORT).show();
                        }
                        if(esOwner.isChecked()){
                            esowner = true;
                        }
                        if(sigue){
                            Usuario aux_insert = new Usuario();
                            aux_insert.setMail(correo_insert);
                            aux_insert.setNombreusuario(nombreuser_insert);
                            aux_insert.setPassword(pass_insert);
                            aux_insert.setPinacceso(pinacceso_insert);
                            aux_insert.setEsOwner(esowner);
                            bdusuarios.InsertaUsuario(aux_insert);
                        }
                    }
                    finish();
                    super.onBackPressed();
                }else{
                    Toast.makeText(this, "Rellena todos los campos, por favor", Toast.LENGTH_SHORT).show();
                }








                break;
            case R.id.delete_usuario:
                Usuario u = new Usuario();
                u.setIdusuario(Integer.parseInt(id_usuario));
                bdusuarios.BorraUsuario(u);

                finish();
                super.onBackPressed();
                break;
            case R.id.cancel_boton_usuario:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
