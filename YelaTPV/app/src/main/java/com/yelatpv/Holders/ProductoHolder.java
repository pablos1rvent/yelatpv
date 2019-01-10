package com.yelatpv.Holders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yelatpv.ClasesOBJ.Categoria;
import com.yelatpv.ClasesOBJ.Producto;
import com.yelatpv.R;

/***
 * Created by alumno on 22/02/2018.
 */

public class ProductoHolder extends RecyclerView.ViewHolder{
    TextView nombreproducto;
    TextView referenciaunica;
    ImageView imagenprod;
    private View.OnClickListener listener;
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    public ProductoHolder(View itemView) {
        super(itemView);
        nombreproducto=(TextView)itemView.findViewById(R.id.nombreproducto);
        referenciaunica=(TextView)itemView.findViewById(R.id.referenciaunica);
        imagenprod=itemView.findViewById(R.id.imagenprod);
    }
    public void bind (final Producto cat)
    {
        nombreproducto.setText(cat.getNombreproducto());
        referenciaunica.setText(cat.getReferenciaunica());
        imagenprod.setImageBitmap(StringToBitMap(cat.getImagen()));

    }

}