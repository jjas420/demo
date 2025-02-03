package com.spring.bakend.jonathan.usersapp.demo.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.spring.bakend.jonathan.usersapp.demo.entities.Proveedor;
import com.spring.bakend.jonathan.usersapp.demo.entities.User;
import com.spring.bakend.jonathan.usersapp.demo.models.UserRequest;
import com.spring.bakend.jonathan.usersapp.demo.services.ProveedorService;
import com.spring.bakend.jonathan.usersapp.demo.services.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/api/proveedor")
public class ProveedorController {

    @Autowired
    private ProveedorService service;
      @GetMapping
      @PreAuthorize("permitAll()")
    public List<Proveedor> list() {
        return service.findAll();
    }

    @GetMapping("/buscar/{name}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
      public List<Proveedor> buscarporNombre(@PathVariable String name) {
          return service.findByNameContainingNative(name);
      }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<Proveedor> ProveedorOptional = service.findById(id);
        if (ProveedorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(ProveedorOptional.orElseThrow());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "el Proveedor no se encontro por el id:" + id ));
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> create(@Valid @RequestBody Proveedor proveedor, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(proveedor));
        
        } catch (IllegalArgumentException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", "BAD_REQUEST");
            errors.put("message", e.getMessage());  // El mensaje del error de unicidad

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        } catch (Exception e) {
            // Manejo de otros errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el proveedor");
        }

        
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Proveedor Proveedor, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validation(result);
        }
        try{
        Optional<Proveedor> userOptional = service.update(Proveedor,id);
        if (userOptional.isPresent()) {
       
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
        
         
    } catch (IllegalArgumentException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "BAD_REQUEST");
        errors.put("message", e.getMessage());  // El mensaje del error de unicidad

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    } catch (Exception e) {
        // Manejo de otros errores
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el Proveedor");
    }
}

  @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Proveedor> userOptional = service.findById(id);
        if (userOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.ok("El registro con ID " + id + " ha sido eliminado correctamente.");
        }
        return ResponseEntity.notFound().build();
    }


    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            if (result.hasErrors()) 

            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }




}
