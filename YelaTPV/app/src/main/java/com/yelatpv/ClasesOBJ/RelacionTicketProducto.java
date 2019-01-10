package com.yelatpv.ClasesOBJ;

/**
 * Created by pablosirvent on 20/5/18.
 */

public class RelacionTicketProducto {
    Integer idrelacionticketproducto;
    Integer idticket;
    Integer idproducto;
    Integer unidades;
    Float precioxproducto;
    Float porcentajeiva;

    public RelacionTicketProducto() {
    }

    public Integer getIdrelacionticketproducto() {
        return idrelacionticketproducto;
    }

    public void setIdrelacionticketproducto(Integer idrelacionticketproducto) {
        this.idrelacionticketproducto = idrelacionticketproducto;
    }

    public Integer getIdticket() {
        return idticket;
    }

    public void setIdticket(Integer idticket) {
        this.idticket = idticket;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    public Float getPrecioxproducto() {
        return precioxproducto;
    }

    public void setPrecioxproducto(Float precioxproducto) {
        this.precioxproducto = precioxproducto;
    }

    public Float getPorcentajeiva() {
        return porcentajeiva;
    }

    public void setPorcentajeiva(Float porcentajeiva) {
        this.porcentajeiva = porcentajeiva;
    }

    public RelacionTicketProducto(Integer idrelacionticketproducto, Integer idticket, Integer idproducto, Integer unidades, Float precioxproducto, Float porcentajeiva) {
        this.idrelacionticketproducto = idrelacionticketproducto;
        this.idticket = idticket;
        this.idproducto = idproducto;
        this.unidades = unidades;
        this.precioxproducto = precioxproducto;
        this.porcentajeiva = porcentajeiva;
    }
}
