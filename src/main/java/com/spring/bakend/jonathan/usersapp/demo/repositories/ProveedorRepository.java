package com.spring.bakend.jonathan.usersapp.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.bakend.jonathan.usersapp.demo.entities.Proveedor;
import com.spring.bakend.jonathan.usersapp.demo.entities.User;

public interface ProveedorRepository extends JpaRepository<Proveedor,Long> {

    boolean existsByNombre(String nombre);

      @Query(value = "SELECT * FROM proveedor WHERE nombre LIKE CONCAT('%', :name, '%')", nativeQuery = true)
    List<Proveedor> findByNameContainingNative(@Param("name") String name);
    

}
