package com.spring.bakend.jonathan.usersapp.demo.controllers;

import java.util.List;

import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;

public class entradaRequest {
    private List<Producto> productos;
    private String observacion;
    private double totalEngeneral;


   
    public double getTotalEngeneral() {
        return totalEngeneral;
    }

    public void setTotalEngeneral(double totalEngeneral) {
        this.totalEngeneral = totalEngeneral;
    }

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
