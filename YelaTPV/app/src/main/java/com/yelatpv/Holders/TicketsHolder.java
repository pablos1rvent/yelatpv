package com.yelatpv.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yelatpv.ClasesOBJ.Producto;
import com.yelatpv.ClasesOBJ.Tickets;
import com.yelatpv.R;

/***
 * Created by alumno on 22/02/2018.
 */

public class TicketsHolder extends RecyclerView.ViewHolder{
    TextView idticket;
    TextView precio_total;
    private View.OnClickListener listener;

    public TicketsHolder(View itemView) {
        super(itemView);
        idticket=(TextView)itemView.findViewById(R.id.id_ticket);
        precio_total=(TextView)itemView.findViewById(R.id.precio_total);
    }
    public void bind (final Tickets t)
    {
        idticket.setText("#TPV"+t.getIdticket());
        String formapago = "";
        if(t.getTipoPago() == 0){
            formapago = "Efectivo";
        }else{
            formapago = "Tarjeta de crédito";
        }
        precio_total.setText(t.getPreciototal().toString() + "€" + " - " + formapago);

    }

}