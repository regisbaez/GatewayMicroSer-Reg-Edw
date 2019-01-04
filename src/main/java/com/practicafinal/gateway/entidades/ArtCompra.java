package com.practicafinal.gateway.entidades;

import java.time.Instant;
import java.util.Date;


public class ArtCompra {


    private Long id;

    private Art articulo;

    private Boolean entregado;

    private int cantidad;

    private Date fecha;

    public ArtCompra() {
        entregado = false;
        cantidad = 0;
        fecha = Date.from(Instant.now());
    }

    public ArtCompra(Art articulo, Boolean entregado, int cantidad, Date fecha) {
        this.articulo = articulo;
        this.entregado = entregado;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Art getArticulo() {
        return articulo;
    }

    public void setArticulo(Art articulo) {
        this.articulo = articulo;
    }

    public Boolean getEntregado() {
        return entregado;
    }

    public void setEntregado(Boolean entregado) {
        this.entregado = entregado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
