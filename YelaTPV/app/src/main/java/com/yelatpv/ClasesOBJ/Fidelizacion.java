package com.yelatpv.ClasesOBJ;

/**
 * Created by pablosirvent on 20/5/18.
 */

public class Fidelizacion {
    String idfidelizacion;
    String codigo;
    Integer modo_fidelizacion;
    Float cantidad_fidelizacion;

    public Fidelizacion(String idfidelizacion, String codigo, Integer modo_fidelizacion, Float cantidad_fidelizacion) {
        this.idfidelizacion = idfidelizacion;
        this.codigo = codigo;
        this.modo_fidelizacion = modo_fidelizacion;
        this.cantidad_fidelizacion = cantidad_fidelizacion;
    }

    public Fidelizacion() {
    }

    public String getIdfidelizacion() {
        return idfidelizacion;
    }

    public void setIdfidelizacion(String idfidelizacion) {
        this.idfidelizacion = idfidelizacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getModo_fidelizacion() {
        return modo_fidelizacion;
    }

    public void setModo_fidelizacion(Integer modo_fidelizacion) {
        this.modo_fidelizacion = modo_fidelizacion;
    }

    public Float getCantidad_fidelizacion() {
        return cantidad_fidelizacion;
    }

    public void setCantidad_fidelizacion(Float cantidad_fidelizacion) {
        this.cantidad_fidelizacion = cantidad_fidelizacion;
    }
}
