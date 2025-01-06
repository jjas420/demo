package com.spring.bakend.jonathan.usersapp.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.bakend.jonathan.usersapp.demo.entities.User;

public interface UserService {
    
    Page<User> findAll(Pageable Peageable);

    List<User> findAll();
    Optional <User>  findById(Long id);
    User save (User user);
    void deleteById (Long id);

    

}
