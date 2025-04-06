package com.spring.bakend.jonathan.usersapp.demo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.bakend.jonathan.usersapp.demo.entities.EntradaProducto;
import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;
import com.spring.bakend.jonathan.usersapp.demo.entities.ProductoSalidad;
import com.spring.bakend.jonathan.usersapp.demo.entities.SalidadProductos;
import com.spring.bakend.jonathan.usersapp.demo.repositories.SalidadProductoRepository;
@Service
public class SalidadProductoServiceImplement  implements SalidadProductoService{

    @Autowired
    SalidadProductoRepository salidadProductoRepository;

    @Autowired
    ProductoService productoService;
    @Override
    public List<SalidadProductos> findAll() {
      return  salidadProductoRepository.findAll();
       
    }

    @Override
    public Optional<SalidadProductos> findById(@NonNull Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
        @Transactional

    public SalidadProductos save(SalidadProductos salidadProductos) {
      
    return salidadProductoRepository.save(salidadProductos);
    }

    @Override
    public Optional<SalidadProductos> update(SalidadProductos salidadProductos, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }




}
