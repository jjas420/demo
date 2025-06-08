package com.spring.bakend.jonathan.usersapp.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;
import com.spring.bakend.jonathan.usersapp.demo.entities.Proveedor;

public interface ProductoService {
    List<Producto> findAll();

    Optional<Producto> findById(@NonNull Long id);

    Producto save(Producto producto);

    Optional<Producto> update(Producto producto, Long id);

    void deleteById(Long id);

    boolean validarProducto(Producto producto);
    List<Producto> guardarProductos(List<Producto> productos); 
    List<Producto> listaDeProductosProvedor(Long id);

    List<Producto> findByNameContainingNative(@Param("name") String name, @Param("idProveedor") Long idProveedor);

    List<Producto> findByName(@Param("name") String name);

    Optional<Producto> findByCodigoProducto(@Param("codigo") String codigo);

    




}
