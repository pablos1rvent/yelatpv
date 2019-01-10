package com.yelatpv.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yelatpv.ClasesOBJ.Categoria;
import com.yelatpv.Interfaces.OnCategoriaSelectedListener;
import com.yelatpv.R;

/**
 * Created by alumno on 22/02/2018.
 */

public class CategoriaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView categorianombre;
    private View.OnClickListener listener;

    public CategoriaHolder(View itemView) {
        super(itemView);
        categorianombre=(TextView)itemView.findViewById(R.id.nombrecategoria);
    }
    public void bind (final Categoria cat)
    {
        categorianombre.setText(cat.getNombrecategoria());
    }
    public void Click(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

}