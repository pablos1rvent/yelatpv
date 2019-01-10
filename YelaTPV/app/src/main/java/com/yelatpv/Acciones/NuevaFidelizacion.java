package com.yelatpv.Acciones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yelatpv.ClasesOBJ.BDCategorias;
import com.yelatpv.ClasesOBJ.BDFidelizacion;
import com.yelatpv.ClasesOBJ.BDProductos;
import com.yelatpv.ClasesOBJ.Categoria;
import com.yelatpv.ClasesOBJ.Fidelizacion;
import com.yelatpv.ClasesOBJ.Producto;
import com.yelatpv.R;

import java.util.ArrayList;

public class NuevaFidelizacion extends AppCompatActivity{

    TextView codigoticket;
    TextView cantidad;
    RadioButton euros;
    RadioButton porcentaje;
    boolean modo_edicion = false;
    String idfidelizacion = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_fidelizacion);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        codigoticket = (TextView) this.findViewById(R.id.codigoticket);
        cantidad = (TextView) this.findViewById(R.id.cantidad);
        euros = this.findViewById(R.id.euros);
        porcentaje = this.findViewById(R.id.porcentaje);


        if(bd != null)
        {
            idfidelizacion = (String) bd.get("idfidelizacion");
            BDFidelizacion bdfidelizacion = new BDFidelizacion(this, "fidelizacion", null, 1);
            ArrayList<Fidelizacion> fidelizacion_arr = bdfidelizacion.devuelveArrayListPorCursor(bdfidelizacion.getCursorFidelizacionporID(idfidelizacion));
            codigoticket.setText(fidelizacion_arr.get(0).getCodigo());
            cantidad.setText(fidelizacion_arr.get(0).getCantidad_fidelizacion().toString());
            if(fidelizacion_arr.get(0).getModo_fidelizacion() == 0){
                euros.setChecked(true);
            }else{
                porcentaje.setChecked(true);
            }
            modo_edicion = true;

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        BDFidelizacion bdfidelizacion = new BDFidelizacion(this, "fidelizacion", null, 1);
        switch(item.getItemId()){
            case R.id.add_boton_fidelizacion:
                String cod_ticket_final = codigoticket.getText().toString();
                String cantidad_final = cantidad.getText().toString();
                if(!cod_ticket_final.equals("")){
                    if(!cantidad_final.equals("")){
                        boolean apto = false;
                        if(euros.isChecked()){
                            apto = true;
                        }else{
                            if(porcentaje.isChecked()){
                                apto=true;
                            }else{
                                apto = false;
                            }
                        }
                        if(apto){
                            Fidelizacion f = new Fidelizacion();
                            f.setCodigo(cod_ticket_final);
                            f.setCantidad_fidelizacion(Float.parseFloat(cantidad_final));
                            if(euros.isChecked()){
                                f.setModo_fidelizacion(0);
                            }else{
                                f.setModo_fidelizacion(1);
                            }
                            if(modo_edicion){
                                f.setIdfidelizacion(idfidelizacion);
                                bdfidelizacion.ActualizaFidelizacion(f);
                            }else{
                                bdfidelizacion.InsertaFidelizacion(f);
                            }

                            finish();
                            super.onBackPressed();
                        }else{
                            Toast.makeText(this, "Selecciona euros o porcentaje, por favor", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(this, "Inserta una cantidad, por favor", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this, "Inserta un código, por favor", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.delete_fidelizacion:
                Fidelizacion f = new Fidelizacion();
                f.setIdfidelizacion(idfidelizacion);
                bdfidelizacion.BorraFidelizacion(f);
                finish();
                super.onBackPressed();
                break;
            case R.id.cancel_boton_fidelizacion:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nueva_fidelizacion_menu, menu);

        MenuItem botonborrar = menu.findItem(R.id.delete_fidelizacion);
        if(modo_edicion){
            botonborrar.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }


}
