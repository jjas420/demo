package com.spring.bakend.jonathan.usersapp.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import static jakarta.persistence.GenerationType.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class EntradaProductoProducto {
   
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
     
    @ManyToOne
    @JoinColumn(name = "entrada_producto_id")
    @JsonBackReference
    private EntradaProducto entradaProducto;

    
    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties("productos") // Evita el bucle aquí
    private Producto producto;

    private String responsable;

    private Long cantidad;

     private  double totalPorProducto;
    private double totalEngeneral;
    


  


    public double getTotalPorProducto() {
        return totalPorProducto;
    }


    public void setTotalPorProducto(double totalPorProducto) {
        this.totalPorProducto = totalPorProducto;
    }


    public double getTotalEngeneral() {
        return totalEngeneral;
    }


    public void setTotalEngeneral(double totalEngeneral) {
        this.totalEngeneral = totalEngeneral;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public EntradaProducto getEntradaProducto() {
        return entradaProducto;
    }


    public void setEntradaProducto(EntradaProducto entradaProducto) {
        this.entradaProducto = entradaProducto;
    }


    public Producto getProducto() {
        return producto;
    }


    public void setProducto(Producto producto) {
        this.producto = producto;
    }


    public Long getCantidad() {
        return cantidad;
    }


    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }


    public String getResponsable() {
        return responsable;
    }


    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }


  
   


  




}
