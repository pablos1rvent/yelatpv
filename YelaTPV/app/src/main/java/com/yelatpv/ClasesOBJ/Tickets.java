package com.yelatpv.ClasesOBJ;

/**
 * Created by pablosirvent on 20/5/18.
 */

public class Tickets {
    String idticket;
    String fecha_ticket;
    Float preciototal;
    String codigo_descuento;
    Integer tipoPago;

    public String getIdticket() {
        return idticket;
    }

    public void setIdticket(String idticket) {
        this.idticket = idticket;
    }

    public String getFecha_ticket() {
        return fecha_ticket;
    }

    public void setFecha_ticket(String fecha_ticket) {
        this.fecha_ticket = fecha_ticket;
    }

    public Float getPreciototal() {
        return preciototal;
    }

    public void setPreciototal(Float preciototal) {
        this.preciototal = preciototal;
    }

    public String getCodigo_descuento() {
        return codigo_descuento;
    }

    public void setCodigo_descuento(String codigo_descuento) {
        this.codigo_descuento = codigo_descuento;
    }

    public Integer getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(Integer tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Tickets(String idticket, String fecha_ticket, Float preciototal, String codigo_descuento, Integer tipoPago) {
        this.idticket = idticket;
        this.fecha_ticket = fecha_ticket;
        this.preciototal = preciototal;
        this.codigo_descuento = codigo_descuento;
        this.tipoPago = tipoPago;
    }

    public Tickets() {
    }
}
