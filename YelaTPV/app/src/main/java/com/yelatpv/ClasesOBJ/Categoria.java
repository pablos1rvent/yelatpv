package com.yelatpv.ClasesOBJ;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by pablosirvent on 11/3/18.
 */

public class Categoria implements Serializable{
    Integer idcategoria;
    Integer idpadre;
    Integer idempresa;
    String nombrecategoria;
    Integer activo;
    String color;
    String imagen;


    public Categoria(Integer idcategoria, Integer idpadre, Integer idempresa, String nombrecategoria, Integer activo, String color, String imagen) {
        this.idcategoria = idcategoria;
        this.idpadre = idpadre;
        this.idempresa = idempresa;
        this.nombrecategoria = nombrecategoria;
        this.activo = activo;
        this.color = color;
        this.imagen = imagen;
    }

    public Categoria() {
    }

    public Integer getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Integer getIdpadre() {
        return idpadre;
    }

    public void setIdpadre(Integer idpadre) {
        this.idpadre = idpadre;
    }

    public Integer getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Integer idempresa) {
        this.idempresa = idempresa;
    }

    public String getNombrecategoria() {
        return nombrecategoria;
    }

    public void setNombrecategoria(String nombrecategoria) {
        this.nombrecategoria = nombrecategoria;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
