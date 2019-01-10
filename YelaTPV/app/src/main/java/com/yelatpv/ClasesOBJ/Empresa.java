package com.yelatpv.ClasesOBJ;

import java.io.Serializable;

/**
 * Created by pablosirvent on 11/3/18.
 */

public class Empresa implements Serializable{
    String nifcif;
    String pais;
    String razonsocial;
    String direccion;
    String codigopostal;
    String provincia;
    String ciudad;
    String telefono;
    Boolean iva_general;
    Float iva;

    public Empresa() {
    }

    public Empresa(String pais, String razonsocial, String nifcif, String direccion, String codigopostal, String provincia, String ciudad, String telefono, Boolean iva_general, Float iva) {
        this.pais = pais;
        this.razonsocial = razonsocial;
        this.nifcif = nifcif;
        this.direccion = direccion;
        this.codigopostal = codigopostal;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.iva_general = iva_general;
        this.iva = iva;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getNifcif() {
        return nifcif;
    }

    public void setNifcif(String nifcif) {
        this.nifcif = nifcif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getIva_general() {
        return iva_general;
    }

    public void setIva_general(Boolean iva_general) {
        this.iva_general = iva_general;
    }

    public Float getIva() {
        return iva;
    }

    public void setIva(Float iva) {
        this.iva = iva;
    }
}
