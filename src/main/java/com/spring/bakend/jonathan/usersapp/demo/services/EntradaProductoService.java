package com.spring.bakend.jonathan.usersapp.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.spring.bakend.jonathan.usersapp.demo.entities.EntradaProducto;
import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;
@Service
public interface EntradaProductoService {
     List<EntradaProducto> findAll();

    Optional<EntradaProducto> findById(@NonNull Long id);

    EntradaProducto save(EntradaProducto entradaProducto, List <Producto> productos);

    Optional<EntradaProducto> update(EntradaProducto entradaProducto, List <Producto> productos,  Long id);

    void deleteById(Long id);

}
