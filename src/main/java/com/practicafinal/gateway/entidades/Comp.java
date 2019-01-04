package com.practicafinal.gateway.entidades;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

public class Comp {


    private Long id;

    
    private Client cliente;

    private Set<ArtCompra> articuloCompras;

    private Date fechaCompra;



    public Comp() {

        fechaCompra = Date.from(Instant.now());
    }

    public Comp(Client cliente, Set<ArtCompra> articuloCompras, Date fechaCompra) {
        this.cliente = cliente;
        this.articuloCompras = articuloCompras;
        this.fechaCompra = fechaCompra;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getCliente() {
        return cliente;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public Set<ArtCompra> getArticuloCompras() {
        return articuloCompras;
    }

    public void setArticuloCompras(Set<ArtCompra> articuloCompras) {
        this.articuloCompras = articuloCompras;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
}
