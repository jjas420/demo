package com.spring.bakend.jonathan.usersapp.demo.controllers;

import java.util.List;

import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;

public class entradaRequest {
    private List<Producto> productos;
    private String observacion;

    // Getters y setters
    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

}
