package com.spring.bakend.jonathan.usersapp.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;
import com.spring.bakend.jonathan.usersapp.demo.entities.Proveedor;

public interface ProductoRepository  extends JpaRepository<Producto,Long>{
    Producto findTopByOrderByIdDesc();
    


}
