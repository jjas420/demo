package com.spring.bakend.jonathan.usersapp.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Modifying
@Transactional
@Query(value = "SELECT * FROM productos WHERE proveedor_id=:id", nativeQuery = true)
    List<Producto> listaDeProductosProvedor(Long id);
     @Query(value = "SELECT * \n" + //
                  "FROM productos \n" + //
                  "WHERE nombre LIKE CONCAT('%', :name, '%') \n" + //
                  "AND proveedor_id = :id;", nativeQuery = true)
    List<Producto> findByNameContainingNative(@Param("name") String name,Long id);

    @Query(value = "SELECT * \n" + //
    "FROM productos \n" + //
    "WHERE nombre LIKE CONCAT('%', :name, '%')" , nativeQuery = true)
List<Producto> findByName(@Param("name") String name);

@Query(value = "SELECT * FROM productos WHERE codigo_producto LIKE CONCAT('%', :codigo, '%')", nativeQuery = true)
Optional<Producto>  findByCodigoProducto(@Param("codigo") String codigo);


    

}
