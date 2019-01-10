package com.yelatpv.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yelatpv.ClasesOBJ.Producto;
import com.yelatpv.ClasesOBJ.Usuario;
import com.yelatpv.R;

/***
 * Created by alumno on 22/02/2018.
 */

public class UsuarioHolder extends RecyclerView.ViewHolder{
    TextView nombreusuario;
    TextView status_usuario;
    private View.OnClickListener listener;

    public UsuarioHolder(View itemView) {
        super(itemView);
        nombreusuario=(TextView)itemView.findViewById(R.id.nombreusuario_sistema);
        status_usuario=(TextView)itemView.findViewById(R.id.status_usuario);
    }
    public void bind (final Usuario usr)
    {
        nombreusuario.setText(usr.getNombreusuario());

        if(usr.getEsowner()){
            status_usuario.setText("Administrador" + " - " + usr.getMail());
        }else{
            status_usuario.setText("Trabajador" + " - " + usr.getMail());
        }
    }

}