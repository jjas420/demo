package com.spring.bakend.jonathan.usersapp.demo.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import static jakarta.persistence.GenerationType.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Table;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Temporal;



@Entity
public class EntradaProducto {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "productos" })
    private Proveedor proveedor;

    @OneToMany(mappedBy = "entradaProducto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private List<EntradaProductoProducto> productos;

    private Date fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<EntradaProductoProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<EntradaProductoProducto> productos) {
        this.productos = productos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
