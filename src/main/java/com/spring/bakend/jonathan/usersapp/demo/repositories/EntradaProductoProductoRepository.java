package com.spring.bakend.jonathan.usersapp.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.bakend.jonathan.usersapp.demo.entities.EntradaProductoProducto;
@Repository
public interface EntradaProductoProductoRepository extends JpaRepository<EntradaProductoProducto, Long>  {
      @Modifying
    @Transactional
    @Query(value = "DELETE FROM entrada_producto_producto WHERE id = :id", nativeQuery = true)
    void eliminarProducto( Long id);

}
