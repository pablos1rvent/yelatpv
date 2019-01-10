package com.yelatpv.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yelatpv.ClasesOBJ.Fidelizacion;
import com.yelatpv.R;

/***
 * Created by alumno on 22/02/2018.
 */

public class FidelizacionHolder extends RecyclerView.ViewHolder{
    TextView idfidelizacion;
    TextView cantidad_fidelizascion;
    private View.OnClickListener listener;
    Fidelizacion f;
    public FidelizacionHolder(View itemView) {
        super(itemView);
        idfidelizacion=(TextView)itemView.findViewById(R.id.id_fidelizacion);
        cantidad_fidelizascion=(TextView)itemView.findViewById(R.id.cantidad_fidelizacion);
    }
    public void bind (final Fidelizacion f)
    {
        this.f = f;
        idfidelizacion.setText("#FID"+f.getIdfidelizacion());
        String tipo = "";
        if(f.getModo_fidelizacion() == 0){
            tipo = "€";
        }else{
            tipo = "%";
        }
        cantidad_fidelizascion.setText(f.getCodigo() + " - " + f.getCantidad_fidelizacion() + tipo.toString());

    }


}