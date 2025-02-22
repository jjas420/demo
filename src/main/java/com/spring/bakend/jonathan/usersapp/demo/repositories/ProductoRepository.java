package com.spring.bakend.jonathan.usersapp.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;
import com.spring.bakend.jonathan.usersapp.demo.entities.Proveedor;

public interface ProductoRepository  extends JpaRepository<Producto,Long>{
    Producto findTopByOrderByIdDesc();
    boolean existsByNombre(String nombre);
    Optional<Producto> findByNombre(String nombre);

        @Modifying
    @Transactional
    @Query(value = "DELETE FROM productos WHERE id = :id", nativeQuery = true)
    void eliminarProducto( Long id);

    


    


}
