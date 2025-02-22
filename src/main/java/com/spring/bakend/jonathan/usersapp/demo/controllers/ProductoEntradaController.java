package com.spring.bakend.jonathan.usersapp.demo.controllers;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.bakend.jonathan.usersapp.demo.entities.EntradaProducto;
import com.spring.bakend.jonathan.usersapp.demo.entities.Producto;
import com.spring.bakend.jonathan.usersapp.demo.entities.Proveedor;
import com.spring.bakend.jonathan.usersapp.demo.services.EntradaProductoImpl;
import com.spring.bakend.jonathan.usersapp.demo.services.EntradaProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/entradaProducto")
public class ProductoEntradaController {
    @Autowired
    EntradaProductoService EntradaProductoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
      public ResponseEntity<EntradaProducto> crearEntrada(@RequestBody List<Producto> productos) {
        EntradaProducto nuevaEntrada = new EntradaProducto();
        nuevaEntrada.setFecha(new Date()); // Fecha actual
        Producto producto = productos.get(0);
        

        nuevaEntrada.setProveedor(producto.getProveedor()); // Puedes cambiarlo si lo necesitas

        // Guardamos la entrada con la lista de productos
        EntradaProductoService.save(nuevaEntrada, productos);

        return ResponseEntity.ok(nuevaEntrada);
      }







    @GetMapping
    @PreAuthorize("permitAll()")
    public List<EntradaProducto> list() {
        return EntradaProductoService.findAll();
    }



      @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<EntradaProducto> ProductoOptional = EntradaProductoService.findById(id);
        if (ProductoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(ProductoOptional.orElseThrow());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "el entrada n no se encontro por el id:" + id));
    }

    @PostMapping("/actualizar/{entradaId}")
    @PreAuthorize("hasAnyRole( 'ADMIN')")
    public ResponseEntity<?> actualizarProductos(
            @PathVariable Long entradaId,  // ID de la entrada a actualizar
            @RequestBody List<Producto> productosActualizados  // Lista de productos con las cantidades actualizadas
    ) {
        try {
          EntradaProducto nuevaEntrada = new EntradaProducto();
          nuevaEntrada.setFecha(new Date()); // Fecha actual
          Producto producto = productosActualizados.get(0);
          
  
          nuevaEntrada.setProveedor(producto.getProveedor()); 
            Optional<EntradaProducto> entradaActualizada = EntradaProductoService.update(nuevaEntrada,productosActualizados,entradaId );
            return ResponseEntity.ok(entradaActualizada);  // Retorna la entrada actualizada
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getClass()+"  "+e.getMessage()+ "  "+ e.getCause());  // Retorna error 404 si no se encuentra la entrada
        }
    }

}
