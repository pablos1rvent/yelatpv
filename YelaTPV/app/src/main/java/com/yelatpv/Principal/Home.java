package com.yelatpv.Principal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yelatpv.Acciones.NuevaFidelizacion;
import com.yelatpv.Acciones.NuevoProducto;
import com.yelatpv.Acciones.NuevoUsuario;
import com.yelatpv.Acciones.VerTicket;
import com.yelatpv.ClasesOBJ.BDEmpresa;
import com.yelatpv.ClasesOBJ.BDUsuarios;
import com.yelatpv.ClasesOBJ.Producto;
import com.yelatpv.ClasesOBJ.Tickets;
import com.yelatpv.ClasesOBJ.Usuario;
import com.yelatpv.Fragments.Ajustes;
import com.yelatpv.Fragments.CategoriaGestion;
import com.yelatpv.Fragments.Dashboard;
import com.yelatpv.Fragments.Fidelizacion;
import com.yelatpv.Fragments.MiEmpresa;
import com.yelatpv.Fragments.ProductoGestion;
import com.yelatpv.Fragments.TPV;
import com.yelatpv.Fragments.Tickets_TPV;
import com.yelatpv.Fragments.UsuarioGestion;
import com.yelatpv.Interfaces.OnFidelizacionSelectedListener;
import com.yelatpv.Interfaces.OnProductoSelectedListener;
import com.yelatpv.Interfaces.OnTicketSelectedListener;
import com.yelatpv.Interfaces.OnUsuarioSelectedListener;
import com.yelatpv.R;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class Home extends AppCompatActivity implements OnProductoSelectedListener, OnTicketSelectedListener, OnFidelizacionSelectedListener, OnUsuarioSelectedListener{

    DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    NavigationView navigationView;
    boolean entpv = false;
    boolean nuevo = false;
    Integer id_usuario_logged;

    public static boolean permisos_administracion = false;
    public static boolean iva_general = false;
    public static float iva_total = 0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);




        Boolean nueva_empresa;
        Bundle extras = getIntent().getExtras();
        nueva_empresa = extras.getBoolean("NEW_EMPRESA");
        if(nueva_empresa == true){
            nuevo = true;
            id_usuario_logged = extras.getInt("USER_ID");
        }else{
            id_usuario_logged = Integer.parseInt(extras.getString("IDUSER_LOG"));
        }




        BDUsuarios bdusuarios = new BDUsuarios(this, "Usuarios", null, 1);
        Cursor usuario_cursor = bdusuarios.getUsuarioCursorPorID(id_usuario_logged);


        BDEmpresa bdempresa = new BDEmpresa(this, "Empresa", null, 1);
        Cursor empresas_activas = bdempresa.getEmpresaCursor();



        if (empresas_activas.moveToFirst()){
            while(!empresas_activas.isAfterLast()){
                Integer ivageneral = empresas_activas.getInt(empresas_activas.getColumnIndex("ivageneral"));
                Float ivatot = empresas_activas.getFloat(empresas_activas.getColumnIndex("iva"));

                if(ivageneral == 1){
                    iva_general = true;
                    iva_total = ivatot;
                }else{
                    iva_general = false;
                    iva_total = 0f;
                }
                empresas_activas.moveToNext();
            }
        }
        empresas_activas.close();



        String data = "nodebesalir";
        if (usuario_cursor.moveToFirst()){
            while(!usuario_cursor.isAfterLast()){
                data = usuario_cursor.getString(usuario_cursor.getColumnIndex("nombreusuario"));
                Integer esadmin = usuario_cursor.getInt(usuario_cursor.getColumnIndex("esowner"));

                if(esadmin == 1){
                    permisos_administracion = true;
                }else{
                    permisos_administracion = false;
                }
                usuario_cursor.moveToNext();
            }
        }
        usuario_cursor.close();





        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.hideOverflowMenu();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        setFragment(0);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        setupNavigationDrawerContent(navigationView);

        if(this.permisos_administracion){
            navigationView.getMenu().getItem(6).setVisible(true);
        }else{
            navigationView.getMenu().getItem(6).setVisible(false);
        }
        /*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                accionMenuLateral(item.getItemId());
                mDrawerLayout.closeDrawers();
                return false;
            }
        });*/

        View header = navigationView.getHeaderView(0);
        TextView txt_nombreusuario = (TextView) header.findViewById(R.id.nombreusuario);
        txt_nombreusuario.setText(data);




    }


    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.dashboard:
                                entpv = false;
                                menuItem.setChecked(true);
                                setFragment(0);
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.tpv:
                                entpv = true;
                                menuItem.setChecked(true);
                                setFragment(1);
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.tickets:
                                entpv = false;
                                menuItem.setChecked(true);
                                setFragment(2);
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.fidelizacion:
                                entpv = false;
                                menuItem.setChecked(true);
                                setFragment(3);
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.productogestion:
                                entpv = false;
                                menuItem.setChecked(true);
                                setFragment(4);
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.categoriasgestion:
                                entpv = false;
                                menuItem.setChecked(true);
                                setFragment(5);
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.usuariosgestion:
                                entpv = false;
                                menuItem.setChecked(true);
                                setFragment(6);
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.miempresa:
                                entpv = false;
                                menuItem.setChecked(true);
                                setFragment(7);
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.ajustes:
                                entpv = false;
                                menuItem.setChecked(true);
                                setFragment(8);
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.salir:
                                entpv = false;
                                menuItem.setChecked(true);
                                salirUsuario();
                                mDrawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });
    }

    private void salirUsuario() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    public void cambiarFragmentDesdeOtroFragment(int position){
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.getMenu().getItem(position).setChecked(true);
        setFragment(position);
        if(position == 1){
            entpv = true;
        }
    }
    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                // Dashboard
                Dashboard dashboard_fragment = new Dashboard();
                if(nuevo){
                    dashboard_fragment.setNuevo(true);
                }else{
                    dashboard_fragment.setNuevo(false);
                }
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment, dashboard_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 1:
                // TPV
                TPV tpv_fragment = new TPV();

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment, tpv_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 2:
                //Tickets
                Tickets_TPV tickets_tpv_fragment = new Tickets_TPV();

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment, tickets_tpv_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 3:
                //Fidelizacion
                Fidelizacion fidelizacion_fragment = new Fidelizacion();

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment, fidelizacion_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 4:
                //Producto
                ProductoGestion producto_fragment = new ProductoGestion();

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment, producto_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 5:
                //Categoría
                CategoriaGestion categoria_fragment = new CategoriaGestion();

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment, categoria_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 6:
                //Usuario
                UsuarioGestion usuario_fragment = new UsuarioGestion();

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment, usuario_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 7:
                //Mi empresa
                MiEmpresa miempresa_fragment = new MiEmpresa();

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment, miempresa_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 8:
                //Fidelizacion
                Ajustes ajustes_fragment = new Ajustes();

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.fragment, ajustes_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Confirmar");
                builder.setMessage("Si sales perderás los cambios");

                builder.setPositiveButton("Salir sin guardar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        mDrawerLayout.openDrawer(GravityCompat.START);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                if(entpv){
                    AlertDialog alert = builder.create();
                    alert.show();
                }else{
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }


                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProductoSelected(Producto producto, int posicionPulsada) {
        Intent myIntent = new Intent(Home.this, NuevoProducto.class);
        myIntent.putExtra("idproducto", producto.getIdproducto().toString());
        startActivity(myIntent);
    }

    @Override
    public void onTicketSelected(Tickets ticket, int posicionPulsada) {
        Intent myIntent = new Intent(Home.this, VerTicket.class);
        myIntent.putExtra("idticket", ticket.getIdticket().toString());
        startActivity(myIntent);
    }

    @Override
    public void onFidelizacionSelected(com.yelatpv.ClasesOBJ.Fidelizacion f, int posicionPulsada) {
        Intent myIntent = new Intent(Home.this, NuevaFidelizacion.class);
        myIntent.putExtra("idfidelizacion", f.getIdfidelizacion().toString());
        startActivity(myIntent);
    }

    @Override
    public void onUsuarioSelected(Usuario usu, int posicionPulsada) {
        Intent myIntent = new Intent(Home.this, NuevoUsuario.class);
        myIntent.putExtra("idusuario", usu.getIdusuario().toString());
        startActivity(myIntent);
    }
}
