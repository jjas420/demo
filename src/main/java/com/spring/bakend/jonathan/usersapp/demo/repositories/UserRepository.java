package com.spring.bakend.jonathan.usersapp.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.spring.bakend.jonathan.usersapp.demo.entities.User;

public interface UserRepository extends CrudRepository <User, Long> {
    

    Page<User> findAll(Pageable Peageable);

}
