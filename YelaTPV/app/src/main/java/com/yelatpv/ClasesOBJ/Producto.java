package com.yelatpv.ClasesOBJ;

import java.io.Serializable;

/**
 * Created by pablosirvent on 11/3/18.
 */

public class Producto implements Serializable{
    Integer idproducto;
    String nombreproducto;
    String descripcionproducto;
    Float preciosinimpuestos;
    Float porcentajeimpuestos;
    String marca;
    String referenciaunica;
    String barcode;
    Integer stock;
    String imagen;

    public Producto() {
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public String getDescripcionproducto() {
        return descripcionproducto;
    }

    public void setDescripcionproducto(String descripcionproducto) {
        this.descripcionproducto = descripcionproducto;
    }

    public Float getPreciosinimpuestos() {
        return preciosinimpuestos;
    }

    public void setPreciosinimpuestos(Float preciosinimpuestos) {
        this.preciosinimpuestos = preciosinimpuestos;
    }

    public Float getPorcentajeimpuestos() {
        return porcentajeimpuestos;
    }

    public void setPorcentajeimpuestos(Float porcentajeimpuestos) {
        this.porcentajeimpuestos = porcentajeimpuestos;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getReferenciaunica() {
        return referenciaunica;
    }

    public void setReferenciaunica(String referenciaunica) {
        this.referenciaunica = referenciaunica;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Producto(Integer idproducto, String nombreproducto, String descripcionproducto, Float preciosinimpuestos, Float porcentajeimpuestos, String marca, String referenciaunica, String barcode, Integer stock, String imagen) {
        this.idproducto = idproducto;
        this.nombreproducto = nombreproducto;
        this.descripcionproducto = descripcionproducto;
        this.preciosinimpuestos = preciosinimpuestos;
        this.porcentajeimpuestos = porcentajeimpuestos;
        this.marca = marca;
        this.referenciaunica = referenciaunica;
        this.barcode = barcode;
        this.stock = stock;
        this.imagen = imagen;
    }
}
