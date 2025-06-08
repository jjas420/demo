package com.spring.bakend.jonathan.usersapp.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.bakend.jonathan.usersapp.demo.entities.Proveedor;
import com.spring.bakend.jonathan.usersapp.demo.entities.SalidadProductos;

public interface SalidadProductoRepository  extends JpaRepository<SalidadProductos,Long>  {

}
