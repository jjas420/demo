package com.spring.bakend.jonathan.usersapp.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;

import com.spring.bakend.jonathan.usersapp.demo.entities.EntradaProducto;
import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;
import com.spring.bakend.jonathan.usersapp.demo.entities.SalidadProductos;

public interface SalidadProductoService {

    List<SalidadProductos> findAll();

    Optional<SalidadProductos> findById(@NonNull Long id);

    SalidadProductos save(SalidadProductos salidadProductos);
 
    Optional<SalidadProductos> update(SalidadProductos salidadProductos, Long id);

    void deleteById(Long id);

}
