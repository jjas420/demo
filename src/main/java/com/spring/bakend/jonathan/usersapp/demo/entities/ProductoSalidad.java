package com.spring.bakend.jonathan.usersapp.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductoSalidad {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
      @ManyToOne
    @JoinColumn(name = "producto_id")
    

    private Producto producto;

@ManyToOne
@JoinColumn(name = "salida_id")
@JsonBackReference
private SalidadProductos salida;


    public Long getId() {
        return id;
    }

     public void setId(Long id) {
         this.id = id;
     }

     public Long getCantidad() {
         return cantidad;
     }

     public void setCantidad(Long cantidad) {
         this.cantidad = cantidad;
     }

     public Producto getProducto() {
         return producto;
     }

     public void setProducto(Producto producto) {
         this.producto = producto;
     }

     public SalidadProductos getSalida() {
         return salida;
     }

     public void setSalida(SalidadProductos salida) {
         this.salida = salida;
     }

    private Long cantidad;

  

}
