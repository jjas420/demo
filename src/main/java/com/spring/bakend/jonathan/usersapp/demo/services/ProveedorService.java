package com.spring.bakend.jonathan.usersapp.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import com.spring.bakend.jonathan.usersapp.demo.entities.Proveedor;
import com.spring.bakend.jonathan.usersapp.demo.entities.User;
import com.spring.bakend.jonathan.usersapp.demo.models.UserRequest;

public interface ProveedorService {
    List<Proveedor> findAll();

    Optional<Proveedor> findById(@NonNull Long id);

    Proveedor save (Proveedor proveedor);

    Optional<Proveedor> update(Proveedor proveedor, Long id);


    void deleteById(Long id);
    
     List<Proveedor> findByNameContainingNative(@Param("name") String name);


}
