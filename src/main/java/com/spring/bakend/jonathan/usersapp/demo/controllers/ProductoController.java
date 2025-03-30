package com.spring.bakend.jonathan.usersapp.demo.controllers;

import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;
import com.spring.bakend.jonathan.usersapp.demo.entities.User;
import com.spring.bakend.jonathan.usersapp.demo.models.UserRequest;
import com.spring.bakend.jonathan.usersapp.demo.services.ProductoService;
import com.spring.bakend.jonathan.usersapp.demo.services.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private ProductoService service;

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<Producto> list() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<Producto> ProductoOptional = service.findById(id);
        if (ProductoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(ProductoOptional.orElseThrow());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "el usuario no se encontro por el id:" + id));
    }

     @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(producto));
        
        } catch (IllegalArgumentException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", "BAD_REQUEST");
            errors.put("message", e.getMessage());  // El mensaje del error de unicidad

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        
        } 

       


        catch (Exception e) {
            
            // Manejo de otros errores
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getClass()+ " Error al guardar el producto "+ producto.getNombre());
        }

        
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Producto producto, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validation(result);
        }
        try{
        Optional<Producto> productoOptional = service.update(producto,id);
        if (productoOptional.isPresent()) {
       
            return ResponseEntity.ok(productoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
        
         
    } catch (IllegalArgumentException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "BAD_REQUEST");
        errors.put("message", e.getMessage());  // El mensaje del error de unicidad

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    
    catch ( DataIntegrityViolationException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "ese nombre ya esta en uso");
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    catch (Exception e) {
        // Manejo de otros errores
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el producto");
    }


       
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Producto> productoOptional = service.findById(id);
        if (productoOptional.isPresent()) {
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

    @PostMapping("validarProducto")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> validar(@Valid @RequestBody Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(service.validarProducto(producto));
        
        } catch (IllegalArgumentException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", "BAD_REQUEST");
            errors.put("message", e.getMessage());  // El mensaje del error de unicidad

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        
        } 

       


        catch (Exception e) {
            
            // Manejo de otros errores
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getClass()+ " Error al guardar el producto "+ producto.getNombre());
        }
        
    }
    @PostMapping("provedor")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Producto> obtenerProductosPorProveedor(@RequestBody Map<String, Long> request) {
    Long proveedorId = request.get("proveedor_id");
    return service.listaDeProductosProvedor(proveedorId);
    }




    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarProductos(
            @RequestParam String name,
            @RequestParam Long idProveedor) {
        
        List<Producto> productos = service.findByNameContainingNative(name, idProveedor);
        
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve 204 si no hay resultados
        }
        
        return ResponseEntity.ok(productos);
    }

}
