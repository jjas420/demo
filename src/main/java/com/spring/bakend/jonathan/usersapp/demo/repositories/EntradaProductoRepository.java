package com.spring.bakend.jonathan.usersapp.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.bakend.jonathan.usersapp.demo.entities.EntradaProducto;
@Repository
public interface EntradaProductoRepository extends JpaRepository<EntradaProducto, Long> {



    
} 


