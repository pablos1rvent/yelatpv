package com.yelatpv.Acciones;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yelatpv.ClasesOBJ.BDCategorias;
import com.yelatpv.ClasesOBJ.BDEmpresa;
import com.yelatpv.ClasesOBJ.BDUsuarios;
import com.yelatpv.ClasesOBJ.Categoria;
import com.yelatpv.Fragments.Ajustes;
import com.yelatpv.Fragments.CategoriaGestion;
import com.yelatpv.Fragments.Dashboard;
import com.yelatpv.Fragments.Fidelizacion;
import com.yelatpv.Fragments.MiEmpresa;
import com.yelatpv.Fragments.ProductoGestion;
import com.yelatpv.Fragments.TPV;
import com.yelatpv.Fragments.Tickets_TPV;
import com.yelatpv.Fragments.UsuarioGestion;
import com.yelatpv.Principal.Home;
import com.yelatpv.R;

public class NuevaCategoria extends AppCompatActivity{

    TextView nombrecategoria;
    boolean modo_edicion = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_categoria);
        nombrecategoria = (TextView) this.findViewById(R.id.nombrecategoria);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nueva_categoria_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        BDCategorias bdcategorias = new BDCategorias(this, "categorias", null, 1);
        switch(item.getItemId()){
            case R.id.add_boton_categoria:
                if(Home.permisos_administracion){
                    String nombrecategoria_bd = nombrecategoria.getText().toString();
                    if(!nombrecategoria_bd.equals("")){
                        Categoria c = new Categoria();
                        c.setIdpadre(0);
                        c.setIdempresa(1);
                        c.setNombrecategoria(nombrecategoria_bd);
                        c.setActivo(1);
                        c.setColor("");
                        c.setImagen("");



                        bdcategorias.InsertaCategoria(c);
                        finish();
                        super.onBackPressed();
                    }else{
                        Toast.makeText(this, "Inserta un nombre, por favor", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Lo siento, no tienes permisos de Administración", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.cancel_boton_categoria:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
