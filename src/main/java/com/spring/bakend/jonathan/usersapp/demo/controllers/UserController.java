package com.spring.bakend.jonathan.usersapp.demo.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.spring.bakend.jonathan.usersapp.demo.entities.User;
import com.spring.bakend.jonathan.usersapp.demo.services.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> list() {
        return service.findAll();
    }
   

    @GetMapping("/{id}")
    public ResponseEntity <?> showById(@PathVariable Long id) {
        Optional<User> userOptional=service.findById(id);
        if(userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(userOptional.orElseThrow());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).
        body(Collections.singletonMap("error", "el usuario con el id "+ id + "no fue encontrado"));

    }

    @PostMapping 
    public ResponseEntity <?> create(@RequestBody User user)   {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));



    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional= service.findById(id);

        if (userOptional.isPresent()){
            User userdb= userOptional.get();
            userdb.setEmail(user.getEmail());
            userdb.setName(user.getName());
            userdb.setUsername(user.getUsername());
            userdb.setPassword(user.getPassword());
            return ResponseEntity.ok( service.save(userdb));

        }
        return ResponseEntity.notFound().build();



        
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        Optional<User> userOptional= service.findById(id);
        if(userOptional.isPresent()){
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    
    



}
