package com.spring.bakend.jonathan.usersapp.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;
import com.spring.bakend.jonathan.usersapp.demo.entities.ProductoSalidad;

public interface ProoductosSalidadRepository extends JpaRepository<ProductoSalidad,Long> {


}
