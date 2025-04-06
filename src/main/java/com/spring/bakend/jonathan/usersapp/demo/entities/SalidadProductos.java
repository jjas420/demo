package com.spring.bakend.jonathan.usersapp.demo.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "salidadProductos")
public class SalidadProductos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    private String motivo;

    private String usuarioResponsable;

    private String observaciones;

    @OneToMany(mappedBy = "salida", cascade = CascadeType.ALL)
    private List<ProductoSalidad> productos;



}
