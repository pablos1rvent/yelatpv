package com.yelatpv.ClasesOBJ;

import java.io.Serializable;

/**
 * Created by pablosirvent on 11/3/18.
 */

public class RelacionCategoriaProducto implements Serializable{
    Integer idrelacion;
    Integer idcategoria;
    Integer idproducto;

    public RelacionCategoriaProducto(Integer idrelacion, Integer idcategoria, Integer idproducto) {
        this.idrelacion = idrelacion;
        this.idcategoria = idcategoria;
        this.idproducto = idproducto;
    }

    public Integer getIdrelacion() {
        return idrelacion;
    }

    public void setIdrelacion(Integer idrelacion) {
        this.idrelacion = idrelacion;
    }

    public Integer getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public RelacionCategoriaProducto() {
    }
}
