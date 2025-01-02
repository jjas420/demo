package com.spring.bakend.jonathan.usersapp.demo.services;

import java.util.List;
import java.util.Optional;

import com.spring.bakend.jonathan.usersapp.demo.entities.User;

public interface UserService {

    List<User> findAll();
    Optional <User>  findById(Long id);
    User save (User user);
    void deleteById (Long id);

    

}
