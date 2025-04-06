package com.spring.bakend.jonathan.usersapp.demo.controllers;


import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.bakend.jonathan.usersapp.demo.entities.EntradaProducto;
import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;
import com.spring.bakend.jonathan.usersapp.demo.entities.Proveedor;
import com.spring.bakend.jonathan.usersapp.demo.entities.SalidadProductos;
import com.spring.bakend.jonathan.usersapp.demo.entities.User;
import com.spring.bakend.jonathan.usersapp.demo.models.UserRequest;
import com.spring.bakend.jonathan.usersapp.demo.services.EntradaProductoService;
import com.spring.bakend.jonathan.usersapp.demo.services.ProveedorService;
import com.spring.bakend.jonathan.usersapp.demo.services.SalidadProductoServiceImplement;
import com.spring.bakend.jonathan.usersapp.demo.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/api/salidadProducto")
public class SalidadProductoController {

    

    @Autowired
    private SalidadProductoServiceImplement service;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SalidadProductos> crearSalidad(@RequestBody SalidadProductos salidadProductos) {
        salidadProductos.setFecha(new Date()); // Fecha actual
        service.save(salidadProductos);
        
        // Guardamos la entrada con la lista de productos

        return ResponseEntity.ok(salidadProductos);
      }


      @GetMapping
      @PreAuthorize("permitAll()")
      public List<SalidadProductos> list() {
          return service.findAll();
      }










}
