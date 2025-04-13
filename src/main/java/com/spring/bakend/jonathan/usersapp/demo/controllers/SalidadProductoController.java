package com.spring.bakend.jonathan.usersapp.demo.controllers;


import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.spring.bakend.jonathan.usersapp.demo.services.excepciónPersonalizadas.ExceptionSalidas;

import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/api/salidadProducto")
public class SalidadProductoController {

    

    @Autowired
    private SalidadProductoServiceImplement service;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crearSalidad(@RequestBody SalidadProductos salidadProductos) {

        salidadProductos.setFecha(new Date());

       try {
        service.save(salidadProductos);
        return ResponseEntity.ok("Salida registrada correctamente.");
    } catch (ExceptionSalidas e) {
        // Crear un mensaje más detallado con los productos con error
        List<Producto> productosConError = e.getProductosConError();
        List<String> detallesError = productosConError.stream()
        .map(producto -> {
            // Aquí deberías agregar una lógica para obtener la cantidad solicitada de alguna forma
            return "Producto: " + producto.getNombre() + ", stock disponible:"+ producto.getStock();

        })
        .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(
            "No se pudo registrar la salida. Productos sin stock suficiente: " + detallesError
        );
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
      }


      @GetMapping
      @PreAuthorize("permitAll()")
      public List<SalidadProductos> list() {
          return service.findAll();
      }

      
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<SalidadProductos> salidadPRodcutos = service.findById(id);
        if (salidadPRodcutos.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(salidadPRodcutos.orElseThrow());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "el Proveedor no se encontro por el id:" + id ));
    }
    

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")

    public ResponseEntity<?> actualizarSalida(@PathVariable Long id, @RequestBody SalidadProductos nuevaSalida) {
        nuevaSalida.setFecha(new Date());

        try {


            // Llamar al servicio para actualizar la salida de productos
            service.update(nuevaSalida, id);
            return ResponseEntity.ok().body("Salida actualizada exitosamente");
        } catch (ExceptionSalidas ex) {
            List<Producto> productosConError = ex.getProductosConError();
            List<String> detallesError = productosConError.stream()
            .map(producto -> {
                // Aquí deberías agregar una lógica para obtener la cantidad solicitada de alguna forma
                return "Producto: " + producto.getNombre() + ", stock disponible:"+ producto.getStock();
    
            })
            .collect(Collectors.toList());
            // En caso de error de stock
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar la salida, productos sin stock: " + detallesError);
        } catch (RuntimeException ex) {
            // En caso de un error genérico (producto no encontrado, etc.)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
        }
    }

}












