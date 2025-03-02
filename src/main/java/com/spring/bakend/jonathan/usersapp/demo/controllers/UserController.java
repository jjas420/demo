package com.spring.bakend.jonathan.usersapp.demo.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.spring.bakend.jonathan.usersapp.demo.entities.User;
import com.spring.bakend.jonathan.usersapp.demo.models.UserRequest;
import com.spring.bakend.jonathan.usersapp.demo.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService service;
  
    @GetMapping
  @PreAuthorize("permitAll()")
    public List<User> list() {
        return service.findAll();
    }
    @GetMapping("/buscar/{name}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
      public List<User> buscarporNombre(@PathVariable String name) {
          return service.findByNameContainingNative(name);
      }



    

    @GetMapping("/page/{page}")
    @PreAuthorize("permitAll()")
    public Page<User> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<User> userOptional = service.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userOptional.orElseThrow());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "el usuario no se encontro por el id:" + id));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
        
        } catch (IllegalArgumentException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", "BAD_REQUEST");
            errors.put("message", e.getMessage());  // El mensaje del error de unicidad

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        } catch (Exception e) {
            // Manejo de otros errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el usuario");
        }

        
    }



    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody UserRequest user, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validation(result);
        }
        try{
        Optional<User> userOptional = service.update(user,id);
        if (userOptional.isPresent()) {
       
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
        
         
    } catch (IllegalArgumentException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "BAD_REQUEST");
        errors.put("message", e.getMessage());  // El mensaje del error de unicidad

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    catch (DataIntegrityViolationException e) {
        Map<String, String> errorsl = new HashMap<>();
        String errorMessage =e.getMessage();
        String regex = "for key '(.*?)'";  // Expresión regular para capturar el texto después de 'for key '
    Pattern pattern = Pattern.compile(regex);
   Matcher matcher = pattern.matcher(errorMessage);
   System.out.println();
   if (matcher.find()) {
    String capturedValue = matcher.group(1);  // Extrae lo que está entre comillas
    System.out.println("Captured value: " + capturedValue);
    System.out.println("!"+capturedValue+"!");
    if(capturedValue.equals("users.username_UNIQUE")){
        errorsl.put("message", "el username ya esta en uso");  


    }else{
        errorsl.put("message", "el email ya esta en uso ");  

    }
}else{
    System.out.println("no hay nada");
}

        errorsl.put("CONFLICT", "CONFLICT");
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorsl);

} catch (Exception e) {
        // Manejo de otros errores
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al acualizar el usuario"+e.getClass());
    }


       
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<User> userOptional = service.findById(id);
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
